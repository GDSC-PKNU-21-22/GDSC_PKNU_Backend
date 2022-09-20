package com.gdscpknu.gdscpknu.notion;

import org.jraf.klibnotion.client.Authentication;
import org.jraf.klibnotion.client.ClientConfiguration;
import org.jraf.klibnotion.client.NotionClient;
import org.jraf.klibnotion.client.blocking.BlockingNotionClient;
import org.jraf.klibnotion.client.blocking.BlockingNotionClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GdscNotion {

    @Value("${testNotion.introduce.token}")
    private String TOKEN;
    @Value("${testNotion.introduce.databaseId}")
    private String DATABASE_ID;
    private final BlockingNotionClient client;

    public GdscNotion(BlockingNotionClient client) {
        this.client = initClient();
    }

    public BlockingNotionClient initClient() {
        NotionClient notionClient = NotionClient.newInstance(
                new ClientConfiguration(
                        new Authentication(TOKEN)
                )
        );
        return BlockingNotionClientUtils.asBlockingNotionClient(notionClient);
    }

    public BlockingNotionClient getClient() {
        return client;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public String getDATABASE_ID() {
        return DATABASE_ID;
    }
}
