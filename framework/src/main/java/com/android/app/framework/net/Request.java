package com.android.app.framework.net;


import java.util.Map;

/**
 * Request 请求
 *
 * @author xuwei19
 * @date 2014年11月26日 下午12:22:50
 */
public class Request {
    public static final int GET = 1;
    public static final int POST = 2;
    /**
     * 业务名称
     */
    private String mAction;
    /**
     * 请求方式 默认get
     */
    private int mType = GET;
    /**
     * 相对路径
     */
    private String mPath;
    /**
     * 业务参数
     */
    private Map<String, String> mParams;
    /**
     * 解析方式
     */
    private ParserType mParserType = ParserType.GSON;

    /**
     * 期望返回类型
     */
    private Class<?> mExpectType;

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        this.mAction = action;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }

    public ParserType getParserType() {
        return mParserType;
    }

    public Class<?> getExpectType() {
        return mExpectType;
    }

    public void setExpectType(Class<?> expectType) {
        this.mExpectType = expectType;
    }

    public void setParserType(ParserType parserType) {
        this.mParserType = parserType;
    }

    static public class ParserType {
        public static ParserType GSON = new ParserType(0);
        public static ParserType CUSTOM = new ParserType(1);
        public static ParserType NONE = new ParserType(2);
        private int mValue;

        private ParserType(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return mValue;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ParserType) {
                ParserType type = (ParserType) o;
                return type.getValue() == getValue();
            } else {
                return false;
            }
        }
    }
}
