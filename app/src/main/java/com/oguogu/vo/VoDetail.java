package com.oguogu.vo;

/**
 * Created by Administrator on 2016-12-29.
 */

public class VoDetail extends VoBase {

    public static class VoComment {

        private String comment_idx;
        private String comment_user_thumb_path;
        private String comment_user_nickname;
        private String comment;
        private String comment_date;
        private boolean is_my_comment;

        public String getComment_user_thumb_path() {
            return comment_user_thumb_path;
        }

        public String getComment_idx() {
            return comment_idx;
        }

        public void setComment_idx(String comment_idx) {
            this.comment_idx = comment_idx;
        }

        public void setComment_user_thumb_path(String comment_user_thumb_path) {
            this.comment_user_thumb_path = comment_user_thumb_path;
        }

        public String getComment_user_nickname() {
            return comment_user_nickname;
        }

        public void setComment_user_nickname(String comment_user_nickname) {
            this.comment_user_nickname = comment_user_nickname;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment_date() {
            return comment_date;
        }

        public void setComment_date(String comment_date) {
            this.comment_date = comment_date;
        }

        public boolean is_my_comment() {
            return is_my_comment;
        }

        public void setIs_my_comment(boolean is_my_comment) {
            this.is_my_comment = is_my_comment;
        }
    }

    public static class VoImagePath {
        private String img_path;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
    }
}
