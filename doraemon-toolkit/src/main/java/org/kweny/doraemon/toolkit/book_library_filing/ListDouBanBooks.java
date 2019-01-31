/*
 * Copyright (C) 2018 Apenk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kweny.doraemon.toolkit.book_library_filing;

import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.htmlparser.visitors.TagFindingVisitor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 从 豆瓣 根据 作者 列出所有书名
 *
 * @author Kweny
 * @since TODO-Kweny version
 */
public class ListDouBanBooks {

    public static void main(String[] args) throws Exception {
        String authorId = "4537266"; // 东野圭吾
        doList(authorId);
    }

    static String baseUrl = "https://book.douban.com/author/%s/books?sortby=time&format=text&start=%s";
    static int startStep = 25;

    static HttpClient client = HttpClient.newBuilder().build();

    public static void doList(String authorId) throws Exception {
        int totalFounded = 0;
        int founded;
        int start = 0;
        do {
            String url = String.format(baseUrl, authorId, start);
            String html = getPageHtml(url);
            founded = parseBookNames(html);
            totalFounded += founded;
            start += startStep;
        } while (founded > 0);

        System.out.println("total: " + totalFounded);
    }

    static String getPageHtml(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    static int parseBookNames(String html) throws ParserException {
        Parser parser = Parser.createParser(html, "UTF-8");
        HasAttributeFilter filter = new HasAttributeFilter("class", "list_view");
        NodeList nodeList = parser.parse(filter);
        Node listViewNode = nodeList.elementAt(0);;

        TagFindingVisitor tableVisitor = new TagFindingVisitor(new String[] {"table"});
        listViewNode.accept(tableVisitor);

        Node table = tableVisitor.getTags(0)[0];

        AtomicInteger founded = new AtomicInteger(0);
        table.accept(new NodeVisitor() {
            @Override
            public void visitTag(Tag tag) {
                if (StringUtils.equalsAnyIgnoreCase("a", tag.getTagName())) {
                    String href = tag.getAttribute("href");
                    if (StringUtils.startsWithIgnoreCase(href, "https://book.douban.com/subject")) {
                        founded.incrementAndGet();
                        System.out.println(tag.toPlainTextString() + "\t" + href);
                    }
                }
            }
        });
        return founded.get();
    }
}