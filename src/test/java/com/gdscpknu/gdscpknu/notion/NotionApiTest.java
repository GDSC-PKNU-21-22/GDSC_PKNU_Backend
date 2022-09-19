package com.gdscpknu.gdscpknu.notion;

import org.jraf.klibnotion.client.blocking.BlockingNotionClient;
import org.jraf.klibnotion.model.database.Database;
import org.jraf.klibnotion.model.database.query.DatabaseQuery;
import org.jraf.klibnotion.model.database.query.filter.DatabaseQueryPredicate;
import org.jraf.klibnotion.model.database.query.filter.DatabaseQueryPropertyFilter;
import org.jraf.klibnotion.model.page.Page;
import org.jraf.klibnotion.model.pagination.Pagination;
import org.jraf.klibnotion.model.pagination.ResultPage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

public class NotionApiTest {

    private final NotionTable notionTable = new NotionTable();
    private final BlockingNotionClient client = notionTable.initClient();

    @Test
    public void getTable() {
        //given

        //when
        Database database = client.getDatabases().getDatabase(notionTable.getDATABASE_ID());
        ResultPage<Page> simpleQueryResultPage = client.getDatabases().queryDatabase(
                notionTable.getDATABASE_ID(),
                null,
                null,
                new Pagination()
        );

        //then
//        System.out.println("simpleQueryResultPage = " + simpleQueryResultPage.results);
        assertThat(database.getTitle().getPlainText()).isEqualTo(notionTable.getTableName());
        assertThat(simpleQueryResultPage.results.size()).isEqualTo(notionTable.getMemberNum());
    }

    @Test
    public void getRowByEmail() {
        //given
        String name = "가나다";
        String role = "Core";
        String email = "ganada@pukyong.ac.kr";

        //when
        ResultPage<Page> simpleQueryResultPage = client.getDatabases().queryDatabase(
                notionTable.getDATABASE_ID(),
                new DatabaseQuery()
                        .any(
                                new DatabaseQueryPropertyFilter.Text(
                                        "이메일",
                                        new DatabaseQueryPredicate.Text.Equals(email)
                                )
                        ),
                null,
                new Pagination()
        );

        //then
        assertThat(simpleQueryResultPage.results.size()).isEqualTo(1);
//        System.out.println("simpleQueryResultPage = " + simpleQueryResultPage.results.get(0).getPropertyValues());
        assertThat(simpleQueryResultPage.results.get(0).getPropertyValues().toString().contains(email)).isEqualTo(true);
        assertThat(simpleQueryResultPage.results.get(0).getPropertyValues().toString().contains(role)).isEqualTo(true);
        assertThat(simpleQueryResultPage.results.get(0).getPropertyValues().toString().contains(name)).isEqualTo(true);
    }

    @Test
    void getPlainTexts(){

        //given
        String name = "가나다";
        String role = "Core";
        String email = "ganada@pukyong.ac.kr";

        ResultPage<Page> simpleQueryResultPage = client.getDatabases().queryDatabase(
                notionTable.getDATABASE_ID(),
                null,
                null,
                new Pagination()
        );

        //when
        /**
         * KlibNotion 라이브러리에서 개별 plainText를 받아올 수 없어 정규식으로 추출하였음
         */
        String resultInString = simpleQueryResultPage.results.toString();
        List<String> plainTextList = new ArrayList<>();
        // 문자열 중 "(plainText=" ~ "," 범위(이름, 이메일) OR " "name=" ~ "," 범위(역할) 문자열을 추출
        Pattern pattern = Pattern.compile("(?<=\\(plainText=)(.*?)(?=,)|(?<=\\(name=)(.*?)(?=,)");
        Matcher matcher = pattern.matcher(resultInString);
        while (matcher.find()) {
            plainTextList.add(matcher.group());
        }

        //then
        assertThat(plainTextList.size()).isEqualTo(notionTable.getMemberNum() * 3);
        assertThat(plainTextList.get(0)).isEqualTo(email);
        assertThat(plainTextList.get(1)).isEqualTo(role);
        assertThat(plainTextList.get(2)).isEqualTo(name);
    }

}