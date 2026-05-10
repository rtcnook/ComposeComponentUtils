package com.example.widgetutilslib.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ZipUtils {
    private const val BUFFER_SIZE = 2 * 1024

    /**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构 true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件，会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    @Throws(RuntimeException::class)
    fun toZip(srcDir: String?, out: OutputStream?, KeepDirStructure: Boolean) {
        val start = System.currentTimeMillis()
        var zos: ZipOutputStream? = null
        try {
            zos = ZipOutputStream(out)
            val sourceFile = File(srcDir)
            compress(sourceFile, zos, sourceFile.name, KeepDirStructure)
            val end = System.currentTimeMillis()
            println("压缩完成，耗时：" + (end - start) + " ms")
        } catch (e: Exception) {
            throw RuntimeException("zip error from ZipUtils", e)
        } finally {
            if (zos != null) {
                try {
                    zos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法2
     * @param srcFiles 需要压缩的文件列表
     * @param out           压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    @Throws(RuntimeException::class)
    fun toZip(srcFiles: List<File>, out: OutputStream?) {
        val start = System.currentTimeMillis()
        var zos: ZipOutputStream? = null
        try {
            zos = ZipOutputStream(out)
            for (srcFile in srcFiles) {
                val buf = ByteArray(BUFFER_SIZE)
                zos.putNextEntry(ZipEntry(srcFile.name))
                var len: Int
                val `in` = FileInputStream(srcFile)
                while (`in`.read(buf).also { len = it } != -1) {
                    zos.write(buf, 0, len)
                }
                zos.closeEntry()
                `in`.close()
            }
            val end = System.currentTimeMillis()
            println("压缩完成，耗时：" + (end - start) + " ms")
        } catch (e: Exception) {
            LogUtils.d("toZip error " + e.message)
            throw RuntimeException("zip error from ZipUtils", e)
        } finally {
            if (zos != null) {
                try {
                    zos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构 true:保留目录结构;
     * false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件，会压缩失败)
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun compress(
        sourceFile: File, zos: ZipOutputStream, name: String,
        KeepDirStructure: Boolean
    ) {
        val buf = ByteArray(BUFFER_SIZE)
        if (sourceFile.isFile) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(ZipEntry(name))
            // copy文件到zip输出流中
            var len: Int
            val `in` = FileInputStream(sourceFile)
            while (`in`.read(buf).also { len = it } != -1) {
                zos.write(buf, 0, len)
            }
            // Complete the entry
            zos.closeEntry()
            `in`.close()
        } else {
            val listFiles = sourceFile.listFiles()
            if (listFiles == null || listFiles.size == 0) {
                // 需要保留原来的文件结构，需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(ZipEntry("$name/"))
                    // 没有文件，不需要文件的copy
                    zos.closeEntry()
                }
            } else {
                for (file in listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构 即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.name, KeepDirStructure)
                    } else {
                        compress(file, zos, file.name, KeepDirStructure)
                    }
                }
            }
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        /** 测试压缩方法1  */
        val fos1 = FileOutputStream(File("c:/mytest01.zip"))
        toZip("D:/log", fos1, true)

        /** 测试压缩方法2  */
        val fileList: MutableList<File> = ArrayList()
        fileList.add(File("D:/Java/jdk1.7.0_45_64bit/bin/jar.exe"))
        fileList.add(File("D:/Java/jdk1.7.0_45_64bit/bin/java.exe"))
        val fos2 = FileOutputStream(File("c:/mytest02.zip"))
        toZip(fileList, fos2)
    }
}
