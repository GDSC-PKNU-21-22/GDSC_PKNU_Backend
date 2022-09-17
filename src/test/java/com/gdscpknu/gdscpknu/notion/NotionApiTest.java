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

}