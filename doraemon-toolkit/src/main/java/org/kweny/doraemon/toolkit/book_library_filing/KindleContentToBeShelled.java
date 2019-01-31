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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 将 My Kindle Content 中的书籍文件 copy 到 待脱 DRM 目录中
 *
 * @author Kweny
 * @since 0.0.1
 */
public class KindleContentToBeShelled {

    public static void main(String[] args) {
        File contentFolder = new File("E:\\Storehouse\\Book\\KindlePC\\My Kindle Content");
        String toBeShelledFolderPath = "E:\\Storehouse\\Book\\ePUBee\\待脱DRM\\";

        File[] contents = contentFolder.listFiles();
        if (contents == null || contents.length == 0) {
            System.out.println("content 目录中没有内容");
            return;
        }

        System.out.println("开始拷贝...");

        int totalCount = 0;
        int successCount = 0;
        int failCount = 0;
        for (File content : contents) {
            if (content.isDirectory() && content.getName().toUpperCase().endsWith("_EBOK")) {
                // 以 _EBOK 结尾的文件夹，如 B00BTCMI5S_EBOK

                File[] files = content.listFiles();
                if (files == null || files.length == 0) {
                    continue;
                }
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".azw")) {
                        // 以 .azw 结尾的文件

                        totalCount ++;
                        try {
                            String rdm = UUID.randomUUID().toString().substring(0, 5) + "-";
                            String fileName = rdm + file.getName();
                            Files.copy(file.toPath(), Paths.get(toBeShelledFolderPath, fileName));
                            successCount ++;
                        } catch (Exception e) {
                            failCount ++;
                            System.out.println("一个文件拷贝失败：" + file.getAbsoluteFile());
                        }

                    }
                }
            }
        }

        System.out.println("拷贝完成，总数：" + totalCount + "，成功：" + successCount + "，失败：" + failCount);
    }

}