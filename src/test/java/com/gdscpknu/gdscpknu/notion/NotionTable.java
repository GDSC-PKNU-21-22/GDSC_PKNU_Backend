package com.gdscpknu.gdscpknu.notion;

import org.jraf.klibnotion.client.*;
import org.jraf.klibnotion.client.blocking.BlockingNotionClient;
import org.jraf.klibnotion.client.blocking.BlockingNotionClientUtils;

public class NotionTable {

    private final String TOKEN = "secret_hN9gav0QDqQMB2neSKszfY6jFHyPxpxYNuiMPNU4HKg";
    private final String DATABASE_ID = "1fea9e9c35984f4ba1cd9c114efc7608";
    private final String tableName = "MEMBER PROFILE";
    private final int memberNum = 3;

    public BlockingNotionClient initClient() {
        NotionClient notionClient = NotionClient.newInstance(
                new ClientConfiguration(
                        new Authentication(TOKEN)
                )
        );
        return BlockingNotionClientUtils.asBlockingNotionClient(notionClient);
    }

    public String getDATABASE_ID() {
        return DATABASE_ID;
    }

    public String getTableName() {
        return tableName;
    }

    public int getMemberNum() {
        return memberNum;
    }
}
