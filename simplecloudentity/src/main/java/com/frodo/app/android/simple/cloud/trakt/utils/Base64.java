package com.frodo.app.android.simple.cloud.trakt.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64 {
    public static final int NO_OPTIONS = 0;
    public static final int ENCODE = 1;
    public static final int DECODE = 0;
    public static final int GZIP = 2;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int URL_SAFE = 16;
    public static final int ORDERED = 32;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte EQUALS_SIGN = 61;
    private static final byte NEW_LINE = 10;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final byte[] _STANDARD_ALPHABET = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};
    private static final byte[] _STANDARD_DECODABET = new byte[]{(byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-5, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)62, (byte)-9, (byte)-9, (byte)-9, (byte)63, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)-9, (byte)-9, (byte)-9, (byte)-1, (byte)-9, (byte)-9, (byte)-9, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9};
    private static final byte[] _URL_SAFE_ALPHABET = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)45, (byte)95};
    private static final byte[] _URL_SAFE_DECODABET = new byte[]{(byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-5, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)62, (byte)-9, (byte)-9, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)-9, (byte)-9, (byte)-9, (byte)-1, (byte)-9, (byte)-9, (byte)-9, (byte)0, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)63, (byte)-9, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)37, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9};
    private static final byte[] _ORDERED_ALPHABET = new byte[]{(byte)45, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)95, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122};
    private static final byte[] _ORDERED_DECODABET = new byte[]{(byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-5, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-5, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)0, (byte)-9, (byte)-9, (byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8, (byte)9, (byte)10, (byte)-9, (byte)-9, (byte)-9, (byte)-1, (byte)-9, (byte)-9, (byte)-9, (byte)11, (byte)12, (byte)13, (byte)14, (byte)15, (byte)16, (byte)17, (byte)18, (byte)19, (byte)20, (byte)21, (byte)22, (byte)23, (byte)24, (byte)25, (byte)26, (byte)27, (byte)28, (byte)29, (byte)30, (byte)31, (byte)32, (byte)33, (byte)34, (byte)35, (byte)36, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)37, (byte)-9, (byte)38, (byte)39, (byte)40, (byte)41, (byte)42, (byte)43, (byte)44, (byte)45, (byte)46, (byte)47, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)58, (byte)59, (byte)60, (byte)61, (byte)62, (byte)63, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9, (byte)-9};

    private static final byte[] getAlphabet(int options) {
        return (options & 16) == 16?_URL_SAFE_ALPHABET:((options & 32) == 32?_ORDERED_ALPHABET:_STANDARD_ALPHABET);
    }

    private static final byte[] getDecodabet(int options) {
        return (options & 16) == 16?_URL_SAFE_DECODABET:((options & 32) == 32?_ORDERED_DECODABET:_STANDARD_DECODABET);
    }

    private Base64() {
    }

    private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options);
        return b4;
    }

    private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
        byte[] ALPHABET = getAlphabet(options);
        int inBuff = (numSigBytes > 0?source[srcOffset] << 24 >>> 8:0) | (numSigBytes > 1?source[srcOffset + 1] << 24 >>> 16:0) | (numSigBytes > 2?source[srcOffset + 2] << 24 >>> 24:0);
        switch(numSigBytes) {
            case 1:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = 61;
                destination[destOffset + 3] = 61;
                return destination;
            case 2:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
                destination[destOffset + 3] = 61;
                return destination;
            case 3:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
                destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                return destination;
            default:
                return destination;
        }
    }

    public static void encode(ByteBuffer raw, ByteBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];

        while(raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);
            encoded.put(enc4);
        }

    }

    public static void encode(ByteBuffer raw, CharBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[4];

        while(raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, 0, rem);
            encode3to4(enc4, raw3, rem, 0);

            for(int i = 0; i < 4; ++i) {
                encoded.put((char)(enc4[i] & 255));
            }
        }

    }

    public static String encodeObject(Serializable serializableObject) throws IOException {
        return encodeObject(serializableObject, 0);
    }

    public static String encodeObject(Serializable serializableObject, int options) throws IOException {
        if(serializableObject == null) {
            throw new NullPointerException("Cannot serialize a null object.");
        } else {
            ByteArrayOutputStream baos = null;
            Base64.OutputStream b64os = null;
            GZIPOutputStream gzos = null;
            ObjectOutputStream oos = null;

            try {
                baos = new ByteArrayOutputStream();
                b64os = new Base64.OutputStream(baos, 1 | options);
                if((options & 2) != 0) {
                    gzos = new GZIPOutputStream(b64os);
                    oos = new ObjectOutputStream(gzos);
                } else {
                    oos = new ObjectOutputStream(b64os);
                }

                oos.writeObject(serializableObject);
            } catch (IOException var25) {
                throw var25;
            } finally {
                try {
                    oos.close();
                } catch (Exception var23) {
                    ;
                }

                try {
                    gzos.close();
                } catch (Exception var22) {
                    ;
                }

                try {
                    b64os.close();
                } catch (Exception var21) {
                    ;
                }

                try {
                    baos.close();
                } catch (Exception var20) {
                    ;
                }

            }

            try {
                return new String(baos.toByteArray(), "US-ASCII");
            } catch (UnsupportedEncodingException var24) {
                return new String(baos.toByteArray());
            }
        }
    }

    public static String encodeBytes(byte[] source) {
        String encoded = null;

        try {
            encoded = encodeBytes(source, 0, source.length, 0);
        } catch (IOException var3) {
            assert false : var3.getMessage();
        }

        assert encoded != null;

        return encoded;
    }

    public static String encodeBytes(byte[] source, int options) throws IOException {
        return encodeBytes(source, 0, source.length, options);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        String encoded = null;

        try {
            encoded = encodeBytes(source, off, len, 0);
        } catch (IOException var5) {
            assert false : var5.getMessage();
        }

        assert encoded != null;

        return encoded;
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) throws IOException {
        byte[] encoded = encodeBytesToBytes(source, off, len, options);

        try {
            return new String(encoded, "US-ASCII");
        } catch (UnsupportedEncodingException var6) {
            return new String(encoded);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source) {
        byte[] encoded = null;

        try {
            encoded = encodeBytesToBytes(source, 0, source.length, 0);
        } catch (IOException var3) {
            assert false : "IOExceptions only come from GZipping, which is turned off: " + var3.getMessage();
        }

        return encoded;
    }

    public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options) throws IOException {
        if(source == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if(off < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + off);
        } else if(len < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + len);
        } else if(off + len > source.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(source.length)}));
        } else if((options & 2) != 0) {
            ByteArrayOutputStream var25 = null;
            GZIPOutputStream var26 = null;
            Base64.OutputStream var27 = null;

            try {
                var25 = new ByteArrayOutputStream();
                var27 = new Base64.OutputStream(var25, 1 | options);
                var26 = new GZIPOutputStream(var27);
                var26.write(source, off, len);
                var26.close();
            } catch (IOException var23) {
                throw var23;
            } finally {
                try {
                    var26.close();
                } catch (Exception var22) {
                    ;
                }

                try {
                    var27.close();
                } catch (Exception var21) {
                    ;
                }

                try {
                    var25.close();
                } catch (Exception var20) {
                    ;
                }

            }

            return var25.toByteArray();
        } else {
            boolean breakLines = (options & 8) != 0;
            int encLen = len / 3 * 4 + (len % 3 > 0?4:0);
            if(breakLines) {
                encLen += encLen / 76;
            }

            byte[] outBuff = new byte[encLen];
            int d = 0;
            int e = 0;
            int len2 = len - 2;

            for(int lineLength = 0; d < len2; e += 4) {
                encode3to4(source, d + off, 3, outBuff, e, options);
                lineLength += 4;
                if(breakLines && lineLength >= 76) {
                    outBuff[e + 4] = 10;
                    ++e;
                    lineLength = 0;
                }

                d += 3;
            }

            if(d < len) {
                encode3to4(source, d + off, len - d, outBuff, e, options);
                e += 4;
            }

            if(e <= outBuff.length - 1) {
                byte[] finalOut = new byte[e];
                System.arraycopy(outBuff, 0, finalOut, 0, e);
                return finalOut;
            } else {
                return outBuff;
            }
        }
    }

    private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
        if(source == null) {
            throw new NullPointerException("Source array was null.");
        } else if(destination == null) {
            throw new NullPointerException("Destination array was null.");
        } else if(srcOffset >= 0 && srcOffset + 3 < source.length) {
            if(destOffset >= 0 && destOffset + 2 < destination.length) {
                byte[] DECODABET = getDecodabet(options);
                int outBuff;
                if(source[srcOffset + 2] == 61) {
                    outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12;
                    destination[destOffset] = (byte)(outBuff >>> 16);
                    return 1;
                } else if(source[srcOffset + 3] == 61) {
                    outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12 | (DECODABET[source[srcOffset + 2]] & 255) << 6;
                    destination[destOffset] = (byte)(outBuff >>> 16);
                    destination[destOffset + 1] = (byte)(outBuff >>> 8);
                    return 2;
                } else {
                    outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12 | (DECODABET[source[srcOffset + 2]] & 255) << 6 | DECODABET[source[srcOffset + 3]] & 255;
                    destination[destOffset] = (byte)(outBuff >> 16);
                    destination[destOffset + 1] = (byte)(outBuff >> 8);
                    destination[destOffset + 2] = (byte)outBuff;
                    return 3;
                }
            } else {
                throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", new Object[]{Integer.valueOf(destination.length), Integer.valueOf(destOffset)}));
            }
        } else {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(srcOffset)}));
        }
    }

    public static byte[] decode(byte[] source) throws IOException {
        Object decoded = null;
        byte[] decoded1 = decode(source, 0, source.length, 0);
        return decoded1;
    }

    public static byte[] decode(byte[] source, int off, int len, int options) throws IOException {
        if(source == null) {
            throw new NullPointerException("Cannot decode null source array.");
        } else if(off >= 0 && off + len <= source.length) {
            if(len == 0) {
                return new byte[0];
            } else if(len < 4) {
                throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + len);
            } else {
                byte[] DECODABET = getDecodabet(options);
                int len34 = len * 3 / 4;
                byte[] outBuff = new byte[len34];
                int outBuffPosn = 0;
                byte[] b4 = new byte[4];
                int b4Posn = 0;
                boolean i = false;
                boolean sbiDecode = false;

                for(int var14 = off; var14 < off + len; ++var14) {
                    byte var13 = DECODABET[source[var14] & 255];
                    if(var13 < -5) {
                        throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", new Object[]{Integer.valueOf(source[var14] & 255), Integer.valueOf(var14)}));
                    }

                    if(var13 >= -1) {
                        b4[b4Posn++] = source[var14];
                        if(b4Posn > 3) {
                            outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options);
                            b4Posn = 0;
                            if(source[var14] == 61) {
                                break;
                            }
                        }
                    }
                }

                byte[] out = new byte[outBuffPosn];
                System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
                return out;
            }
        } else {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(off), Integer.valueOf(len)}));
        }
    }

    public static byte[] decode(String s) throws IOException {
        return decode(s, 0);
    }

    public static byte[] decode(String s, int options) throws IOException {
        if(s == null) {
            throw new NullPointerException("Input string was null.");
        } else {
            byte[] bytes;
            try {
                bytes = s.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException var28) {
                bytes = s.getBytes();
            }

            bytes = decode(bytes, 0, bytes.length, options);
            boolean dontGunzip = (options & 4) != 0;
            if(bytes != null && bytes.length >= 4 && !dontGunzip) {
                int head = bytes[0] & 255 | bytes[1] << 8 & '\uff00';
                if('謟' == head) {
                    ByteArrayInputStream bais = null;
                    GZIPInputStream gzis = null;
                    ByteArrayOutputStream baos = null;
                    byte[] buffer = new byte[2048];
                    boolean length = false;

                    try {
                        baos = new ByteArrayOutputStream();
                        bais = new ByteArrayInputStream(bytes);
                        gzis = new GZIPInputStream(bais);

                        int length1;
                        while((length1 = gzis.read(buffer)) >= 0) {
                            baos.write(buffer, 0, length1);
                        }

                        bytes = baos.toByteArray();
                    } catch (IOException var29) {
                        var29.printStackTrace();
                    } finally {
                        try {
                            baos.close();
                        } catch (Exception var27) {
                            ;
                        }

                        try {
                            gzis.close();
                        } catch (Exception var26) {
                            ;
                        }

                        try {
                            bais.close();
                        } catch (Exception var25) {
                            ;
                        }

                    }
                }
            }

            return bytes;
        }
    }

    public static Object decodeToObject(String encodedObject) throws IOException, ClassNotFoundException {
        return decodeToObject(encodedObject, 0, (ClassLoader)null);
    }

    public static Object decodeToObject(String encodedObject, int options, final ClassLoader loader) throws IOException, ClassNotFoundException {
        byte[] objBytes = decode(encodedObject, options);
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Object obj = null;

        try {
            bais = new ByteArrayInputStream(objBytes);
            if(loader == null) {
                ois = new ObjectInputStream(bais);
            } else {
                ois = new ObjectInputStream(bais) {
                    public Class<?> resolveClass(ObjectStreamClass streamClass) throws IOException, ClassNotFoundException {
                        Class c = Class.forName(streamClass.getName(), false, loader);
                        return c == null?super.resolveClass(streamClass):c;
                    }
                };
            }

            obj = ois.readObject();
        } catch (IOException var19) {
            throw var19;
        } catch (ClassNotFoundException var20) {
            throw var20;
        } finally {
            try {
                bais.close();
            } catch (Exception var18) {
                ;
            }

            try {
                ois.close();
            } catch (Exception var17) {
                ;
            }

        }

        return obj;
    }

    public static void encodeToFile(byte[] dataToEncode, String filename) throws IOException {
        if(dataToEncode == null) {
            throw new NullPointerException("Data to encode was null.");
        } else {
            Base64.OutputStream bos = null;

            try {
                bos = new Base64.OutputStream(new FileOutputStream(filename), 1);
                bos.write(dataToEncode);
            } catch (IOException var11) {
                throw var11;
            } finally {
                try {
                    bos.close();
                } catch (Exception var10) {
                    ;
                }

            }

        }
    }

    public static void decodeToFile(String dataToDecode, String filename) throws IOException {
        Base64.OutputStream bos = null;

        try {
            bos = new Base64.OutputStream(new FileOutputStream(filename), 0);
            bos.write(dataToDecode.getBytes("US-ASCII"));
        } catch (IOException var11) {
            throw var11;
        } finally {
            try {
                bos.close();
            } catch (Exception var10) {
                ;
            }

        }

    }

    public static byte[] decodeFromFile(String filename) throws IOException {
        Object decodedData = null;
        Base64.InputStream bis = null;

        try {
            File e = new File(filename);
            Object buffer = null;
            int length = 0;
            boolean numBytes = false;
            if(e.length() > 2147483647L) {
                throw new IOException("File is too big for this convenience method (" + e.length() + " bytes).");
            } else {
                byte[] buffer1 = new byte[(int)e.length()];

                int numBytes1;
                for(bis = new Base64.InputStream(new BufferedInputStream(new FileInputStream(e)), 0); (numBytes1 = bis.read(buffer1, length, 4096)) >= 0; length += numBytes1) {
                    ;
                }

                byte[] decodedData1 = new byte[length];
                System.arraycopy(buffer1, 0, decodedData1, 0, length);
                return decodedData1;
            }
        } catch (IOException var14) {
            throw var14;
        } finally {
            try {
                bis.close();
            } catch (Exception var13) {
                ;
            }

        }
    }

    public static String encodeFromFile(String filename) throws IOException {
        String encodedData = null;
        Base64.InputStream bis = null;

        try {
            File e = new File(filename);
            byte[] buffer = new byte[Math.max((int)((double)e.length() * 1.4D + 1.0D), 40)];
            int length = 0;
            boolean numBytes = false;

            int numBytes1;
            for(bis = new Base64.InputStream(new BufferedInputStream(new FileInputStream(e)), 1); (numBytes1 = bis.read(buffer, length, 4096)) >= 0; length += numBytes1) {
                ;
            }

            encodedData = new String(buffer, 0, length, "US-ASCII");
            return encodedData;
        } catch (IOException var14) {
            throw var14;
        } finally {
            try {
                bis.close();
            } catch (Exception var13) {
                ;
            }

        }
    }

    public static void encodeFileToFile(String infile, String outfile) throws IOException {
        String encoded = encodeFromFile(infile);
        BufferedOutputStream out = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(encoded.getBytes("US-ASCII"));
        } catch (IOException var12) {
            throw var12;
        } finally {
            try {
                out.close();
            } catch (Exception var11) {
                ;
            }

        }

    }

    public static void decodeFileToFile(String infile, String outfile) throws IOException {
        byte[] decoded = decodeFromFile(infile);
        BufferedOutputStream out = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(outfile));
            out.write(decoded);
        } catch (IOException var12) {
            throw var12;
        } finally {
            try {
                out.close();
            } catch (Exception var11) {
                ;
            }

        }

    }

    public static class OutputStream extends FilterOutputStream {
        private boolean encode;
        private int position;
        private byte[] buffer;
        private int bufferLength;
        private int lineLength;
        private boolean breakLines;
        private byte[] b4;
        private boolean suspendEncoding;
        private int options;
        private byte[] decodabet;

        public OutputStream(java.io.OutputStream out) {
            this(out, 1);
        }

        public OutputStream(java.io.OutputStream out, int options) {
            super(out);
            this.breakLines = (options & 8) != 0;
            this.encode = (options & 1) != 0;
            this.bufferLength = this.encode?3:4;
            this.buffer = new byte[this.bufferLength];
            this.position = 0;
            this.lineLength = 0;
            this.suspendEncoding = false;
            this.b4 = new byte[4];
            this.options = options;
            this.decodabet = Base64.getDecodabet(options);
        }

        public void write(int theByte) throws IOException {
            if(this.suspendEncoding) {
                this.out.write(theByte);
            } else {
                if(this.encode) {
                    this.buffer[this.position++] = (byte)theByte;
                    if(this.position >= this.bufferLength) {
                        this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                        this.lineLength += 4;
                        if(this.breakLines && this.lineLength >= 76) {
                            this.out.write(10);
                            this.lineLength = 0;
                        }

                        this.position = 0;
                    }
                } else if(this.decodabet[theByte & 127] > -5) {
                    this.buffer[this.position++] = (byte)theByte;
                    if(this.position >= this.bufferLength) {
                        int len = Base64.decode4to3(this.buffer, 0, this.b4, 0, this.options);
                        this.out.write(this.b4, 0, len);
                        this.position = 0;
                    }
                } else if(this.decodabet[theByte & 127] != -5) {
                    throw new IOException("Invalid character in Base64 data.");
                }

            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            if(this.suspendEncoding) {
                this.out.write(theBytes, off, len);
            } else {
                for(int i = 0; i < len; ++i) {
                    this.write(theBytes[off + i]);
                }

            }
        }

        public void flushBase64() throws IOException {
            if(this.position > 0) {
                if(!this.encode) {
                    throw new IOException("Base64 input not properly padded.");
                }

                this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                this.position = 0;
            }

        }

        public void close() throws IOException {
            this.flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            this.flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = false;
        }
    }

    public static class InputStream extends FilterInputStream {
        private boolean encode;
        private int position;
        private byte[] buffer;
        private int bufferLength;
        private int numSigBytes;
        private int lineLength;
        private boolean breakLines;
        private int options;
        private byte[] decodabet;

        public InputStream(java.io.InputStream in) {
            this(in, 0);
        }

        public InputStream(java.io.InputStream in, int options) {
            super(in);
            this.options = options;
            this.breakLines = (options & 8) > 0;
            this.encode = (options & 1) > 0;
            this.bufferLength = this.encode?4:3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = 0;
            this.decodabet = Base64.getDecodabet(options);
        }

        public int read() throws IOException {
            if(this.position < 0) {
                byte[] b;
                int i;
                int b1;
                if(this.encode) {
                    b = new byte[3];
                    i = 0;

                    for(b1 = 0; b1 < 3; ++b1) {
                        int b2 = this.in.read();
                        if(b2 < 0) {
                            break;
                        }

                        b[b1] = (byte)b2;
                        ++i;
                    }

                    if(i <= 0) {
                        return -1;
                    }

                    Base64.encode3to4(b, 0, i, this.buffer, 0, this.options);
                    this.position = 0;
                    this.numSigBytes = 4;
                } else {
                    b = new byte[4];
                    boolean var6 = false;

                    for(i = 0; i < 4; ++i) {
                        boolean var7 = false;

                        do {
                            b1 = this.in.read();
                        } while(b1 >= 0 && this.decodabet[b1 & 127] <= -5);

                        if(b1 < 0) {
                            break;
                        }

                        b[i] = (byte)b1;
                    }

                    if(i != 4) {
                        if(i == 0) {
                            return -1;
                        }

                        throw new IOException("Improperly padded Base64 input.");
                    }

                    this.numSigBytes = Base64.decode4to3(b, 0, this.buffer, 0, this.options);
                    this.position = 0;
                }
            }

            if(this.position >= 0) {
                if(this.position >= this.numSigBytes) {
                    return -1;
                } else if(this.encode && this.breakLines && this.lineLength >= 76) {
                    this.lineLength = 0;
                    return 10;
                } else {
                    ++this.lineLength;
                    byte var5 = this.buffer[this.position++];
                    if(this.position >= this.bufferLength) {
                        this.position = -1;
                    }

                    return var5 & 255;
                }
            } else {
                throw new IOException("Error in Base64 code reading stream.");
            }
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i;
            for(i = 0; i < len; ++i) {
                int b = this.read();
                if(b < 0) {
                    if(i == 0) {
                        return -1;
                    }
                    break;
                }

                dest[off + i] = (byte)b;
            }

            return i;
        }
    }
}
