package com.zzj.open.base.router;

/**
 * 用于组件开发中，ARouter多Fragment跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterFragmentPath {
    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/home";
        /*首页*/
        public static final String PAGER_HOME = HOME + "/Home";
    }
    /**
     * 新闻组件
     */
    public static class News {
        private static final String NEWS = "/news";
        /*首页*/
        public static final String NEWS_HOME = NEWS + "/News";
    }
    /**
     * 影视组件
     */
    public static class Movie {
        private static final String MOVIE = "/movie";
        /*首页*/
        public static final String MOVIE_HOME = MOVIE + "/Movie";

    }
    /**
     * 聊天组件
     */
    public static class Chat {
        private static final String CHAT = "/chat";
        /*首页*/
        public static final String CHAT_HOME = CHAT + "/Chat";
        //联系人
        public static final String CHAT_CONTACT = CHAT + "/Contact";
        //群组列表
        public static final String CHAT_GROUP = CHAT + "/Group";
    }
    /**
     * 我的组件
     */
    public static class Mine {
        private static final String MINE = "/mine";
        /*首页*/
        public static final String MINE_HOME = MINE + "/Mine";
        //登录
        public static final String MINE_LOGIN = MINE + "/Login";
    }
    /**
     * 福利组件
     */
    public static class Welfare {
        private static final String WELFARE = "/welfare";
        /*首页*/
        public static final String WELFARE_HOME = WELFARE + "/Welfare";
    }

    /**
     * 指纹组件
     */
    public static class Finger{
        private static final String FINGER = "/finger";
        /*指纹弹窗*/
        public static final String PAGER_FINGER = FINGER+ "/Finger";
    }
    /**
     * 工作组件
     */
    public static class Work {
        private static final String WORK = "/work";
        /*工作*/
        public static final String PAGER_WORK = WORK + "/Work";
    }

    /**
     * 消息组件
     */
    public static class Msg {
        private static final String MSG = "/msg";
        /*消息*/
        public static final String PAGER_MSG = MSG + "/msg/Msg";
    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*我的*/
        public static final String PAGER_ME = USER + "/Me";
    }
    /**
     * 游记组件
     */
    public static class Lvji {
        private static final String LVJI = "/lvji";
        /*首页*/
        public static final String PAGER_HOME = LVJI + "/Home";
        /**
         * 发现
         */
        public static final String PAGER_DISCOVER = LVJI + "/Discover";
    }
}
