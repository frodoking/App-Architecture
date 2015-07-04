package com.android.app.framework.filesystem;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

/**
 * InputStream 扩展
 * Created by frodo on 2015/6/24.
 */
public class Input extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private byte[] buf;
    private int pos;
    private int count;
    private InputStream in;

    public Input(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    public Input(InputStream in, int bufferSize) {
        this.in = in;
        buf = new byte[bufferSize];
    }

    public void close() throws IOException {
        buf = null;
        in.close();
    }

    public int read() throws IOException {
        if (pos >= count || pos >= buf.length) {
            pos = 0;
            count = in.read(buf, 0, DEFAULT_BUFFER_SIZE);
        }
        if (pos >= count) {
            return -1;
        }
        return buf[pos++] & 0xff;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int remain = count - pos;
        if (remain >= (len - off)) {
            System.arraycopy(buf, pos, b, off, len);
            pos += len;
            return len;
        } else {
            if (remain > 0) {
                System.arraycopy(buf, pos, b, off, remain);
                pos += remain;
                int newRemain = len - remain;
                byte[] remainData = new byte[newRemain];
                int rd = in.read(remainData, 0, newRemain);
                System.arraycopy(remainData, 0, b, off + remain, rd);
                return remain + rd;
            } else {
                int rd = in.read(b, off, len);
                return rd;
            }
        }
    }

    public boolean readBoolean() throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (ch != 0);
    }

    public int readUnsignedByte() throws IOException {
        int ch = read();
        if (ch < 0) {
            throw new EOFException();
        }
        return ch;
    }

    public int readUnsignedShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (ch1 << 8) + (ch2 << 0);
    }

    public short readShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (short) ((ch1 << 8) + (ch2 << 0));
    }

    public char readChar() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0) {
            throw new EOFException();
        }
        return (char) ((ch1 << 8) + (ch2 << 0));
    }

    public int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0) {
            throw new EOFException();
        }
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    public long readLong() throws IOException {
        return ((long) (readInt()) << 32) + (readInt() & 0xFFFFFFFFL);
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public String readUTF8() throws IOException {
        int utflen = readUnsignedShort();
        if (utflen <= 0) {
            return "";
        }
        char str[] = new char[utflen];
        byte bytearr[] = new byte[utflen];
        int c, char2, char3;
        int count = 0;
        int strlen = 0;
        read(bytearr);
        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    /* 0xxxxxxx */
                    count++;
                    str[strlen++] = (char) c;
                    break;
                case 12:
                case 13:
                    /* 110xxxxx10xxxxxx*/
                    count += 2;
                    if (count > utflen) {
                        throw new UTFDataFormatException();
                    }
                    char2 = (int) bytearr[count - 1];
                    if ((char2 & 0xC0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    str[strlen++] = (char) (((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    /* 1110xxxx10xxxxxx10xxxxxx*/
                    count += 3;
                    if (count > utflen) {
                        throw new UTFDataFormatException();
                    }
                    char2 = (int) bytearr[count - 2];
                    char3 = (int) bytearr[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80)) {
                        throw new UTFDataFormatException();
                    }
                    str[strlen++] = (char) (((c & 0x0F) << 12)
                                                    | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break;
                default:
                /* 10xxxxxx, 1111xxxx*/
                    throw new UTFDataFormatException();
            }
        }
        // The number of chars produced may be less thanutflen
        return new String(str, 0, strlen);
    }

    public Serializable readSerializable() throws IOException {
        String className = readUTF8();
        Serializable serializable = null;
        try {
            serializable = (Serializable) Class.forName(className).newInstance();
            serializable.deserialize(this);
        } catch (Exception e) {
            throw new IOException(e.toString());
        }
        return serializable;
    }

    public byte[] readAll() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch;
        byte[] buffer = new byte[1024];
        while ((ch = in.read(buffer)) != -1) {
            baos.write(buffer, 0, ch);
        }
        byte[] ret = baos.toByteArray();
        baos.close();
        return ret;
    }
}
