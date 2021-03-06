package me.devsaki.hentoid.parsers.images;

import androidx.annotation.NonNull;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.devsaki.hentoid.database.domains.Content;

import static me.devsaki.hentoid.util.network.HttpHelper.getOnlineDocument;

public class ASMHentaiParser extends BaseParser {

    @Override
    protected List<String> parseImages(@NonNull Content content) throws IOException {
        List<String> result = new ArrayList<>();

        // Fetch the reader page
        Document doc = getOnlineDocument(content.getReaderUrl());
        if (doc != null) {
            String imgUrl = "https:" +
                    doc.select("div.full_gallery")
                            .select("a")
                            .select("img")
                            .attr("src");

            String ext = imgUrl.substring(imgUrl.lastIndexOf('.'));

            for (int i = 0; i < content.getQtyPages(); i++) {
                String img = imgUrl.substring(0, imgUrl.lastIndexOf('/') + 1) + (i + 1) + ext;
                result.add(img);
            }
        }

        return result;
    }
}
