package com.gdscpknu.gdscpknu.service;

import com.gdscpknu.gdscpknu.domain.Member;
import com.gdscpknu.gdscpknu.notion.GdscNotion;
import org.jraf.klibnotion.client.blocking.BlockingNotionClient;
import org.jraf.klibnotion.model.page.Page;
import org.jraf.klibnotion.model.pagination.Pagination;
import org.jraf.klibnotion.model.pagination.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {

    private final GdscNotion gdscNotion;
    private final BlockingNotionClient client;
    private List<Member> members;
    private List<String> nameList;
    private List<String> emailList;
    private List<String> roleList;
    private ResultPage<Page> memberIntroPage;

    @Autowired
    public MemberService(GdscNotion gdscNotion) {
        this.gdscNotion = gdscNotion;
        client = gdscNotion.initClient();
    }

    public List<Member> getAllMember() {
        members = new ArrayList<>();
        nameList = new ArrayList<>();
        emailList = new ArrayList<>();
        roleList = new ArrayList<>();

        getMemberIntroPage();

        /**
         * KlibNotion 라이브러리에서 name, email, role 값을 정규식으로 추출하였음
         */
        String resultInString = memberIntroPage.results.toString();
        extractName(resultInString);
        extractEmail(resultInString);
        extractRole(resultInString);
        addMember();

        return members;
    }

    private void getMemberIntroPage() {
        memberIntroPage = client.getDatabases().queryDatabase(
                gdscNotion.getDATABASE_ID(),
                null,
                null,
                new Pagination()
        );
    }

    private void extractName(String resultInString) {
        Pattern namePattern = Pattern.compile("(?<=\\(plainText=)(.*?)(?=,)");
        Matcher nameMatcher = namePattern.matcher(resultInString);
        while (nameMatcher.find()) {
            nameList.add(nameMatcher.group());
        }
    }

    private void extractEmail(String resultInString) {
        Pattern emailPattern = Pattern.compile("(?<=\\bname=이메일, value=)(.*?)(?=\\))");
        Matcher emailMatcher = emailPattern.matcher(resultInString);
        while (emailMatcher.find()) {
            emailList.add(emailMatcher.group());
        }
    }

    private void extractRole(String resultInString) {
        Pattern rolePattern = Pattern.compile("(?<=\\bname=역할, value=SelectOptionImpl\\(name=)(.*?)(?=,)");
        Matcher roleMatcher = rolePattern.matcher(resultInString);
        while (roleMatcher.find()) {
            roleList.add(roleMatcher.group());
        }
    }

    private void addMember() {
        for (int i = 0; i < nameList.size(); i++) {
            Member member = Member.builder()
                    .name(nameList.get(i))
                    .email(emailList.get(i))
                    .role(roleList.get(i))
                    .build();
            members.add(member);
        }
    }
}
