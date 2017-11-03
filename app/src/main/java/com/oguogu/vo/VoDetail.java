package com.oguogu.vo;

/**
 * Created by Administrator on 2016-12-29.
 */

public class VoDetail extends VoBase {

    public static class VoComment {

        private String CommentIdx;
        private String CommentUserThumbPath;
        private String CommentUserId;
        private String Comment;
        private String CommentDate;
        private boolean IsMyComment;

        public String getCommentIdx() {
            return CommentIdx;
        }

        public void setCommentIdx(String commentIdx) {
            CommentIdx = commentIdx;
        }

        public String getCommentUserThumbPath() {
            return CommentUserThumbPath;
        }

        public void setCommentUserThumbPath(String commentUserThumbPath) {
            CommentUserThumbPath = commentUserThumbPath;
        }

        public String getCommentUserId() {
            return CommentUserId;
        }

        public void setCommentUserId(String commentUserId) {
            CommentUserId = commentUserId;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getCommentDate() {
            return CommentDate;
        }

        public void setCommentDate(String commentDate) {
            CommentDate = commentDate;
        }

        public boolean isMyComment() {
            return IsMyComment;
        }

        public void setMyComment(boolean myComment) {
            IsMyComment = myComment;
        }
    }
}
