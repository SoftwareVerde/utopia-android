package com.softwareverde.utopia;

import com.softwareverde.utopia.bundle.Bundle;
import com.softwareverde.utopia.bundle.ForumTopicBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostBundle;
import com.softwareverde.utopia.bundle.ForumTopicPostsBundle;
import com.softwareverde.utopia.bundle.ForumTopicsBundle;

import java.util.ArrayList;
import java.util.List;

public class Forum {
    public static class ForumTopicPost {
        public static ForumTopicPost fromBundle(final ForumTopicPostBundle postBundle) {
            final String topicId = postBundle.get(ForumTopicPostBundle.Keys.TOPIC_ID);
            final String sequenceNumber = postBundle.get(ForumTopicPostBundle.Keys.SEQUENCE_NUMBER);
            final String postDate = postBundle.get(ForumTopicPostBundle.Keys.POST_DATE);
            final String poster = postBundle.get(ForumTopicPostBundle.Keys.POSTER);
            final String content = postBundle.get(ForumTopicPostBundle.Keys.CONTENT);

            final ForumTopicPost topicPost = new ForumTopicPost();
            topicPost.topicId = Util.parseInt(topicId);
            topicPost.sequenceNumber = Util.parseInt(sequenceNumber);
            topicPost.postTick = UtopiaUtil.countTicksByDate(postDate);
            topicPost.poster = poster;
            topicPost.content = content;
            return topicPost;
        }

        public Integer topicId;
        public Integer sequenceNumber;
        public Integer postTick;
        public String poster;
        public String content;
    }

    public static class ForumTopic {
        public static ForumTopic fromBundle(final ForumTopicBundle topicBundle) {
            final String title = topicBundle.get(ForumTopicBundle.Keys.TITLE);
            final String creator = topicBundle.get(ForumTopicBundle.Keys.CREATOR);
            final String lastPostTime = topicBundle.get(ForumTopicBundle.Keys.LAST_POST);
            final String postCount = topicBundle.get(ForumTopicBundle.Keys.POST_COUNT);
            final String topicId = topicBundle.get(ForumTopicBundle.Keys.ID);

            final ForumTopic forumTopic = new ForumTopic();
            forumTopic.title = title;
            forumTopic.creator = creator;
            forumTopic.lastPostTick = UtopiaUtil.countTicksByDate(lastPostTime);
            forumTopic.postCount = Util.parseInt(postCount);
            forumTopic.topicId = Util.parseInt(topicId);
            return forumTopic;
        }

        public ForumTopicPost generateReply(final String replyContent) {
            final ForumTopicPost forumTopicPost = new ForumTopicPost();
            forumTopicPost.topicId = this.topicId;
            forumTopicPost.sequenceNumber = this.postCount + 1;
            forumTopicPost.poster = "";
            forumTopicPost.postTick = 0;
            forumTopicPost.content = replyContent;
            return forumTopicPost;
        }

        public Integer topicId;
        public Integer postCount;
        public Integer lastPostTick;
        public String title;
        public String creator;
        public String content; // NOTE: Used for creating a new topic.
        public List<ForumTopicPost> posts = new ArrayList<ForumTopicPost>();
    }

    private List<ForumTopic> _forumTopics = new ArrayList<ForumTopic>();

    public void clearTopics() {
        _forumTopics.clear();
    }

    public void loadTopics(final ForumTopicsBundle topicsBundle) {
        List<Bundle> bundles = topicsBundle.getGroup(ForumTopicsBundle.Keys.TOPICS);
        if (bundles == null) {
            return;
        }

        for (Integer i=0; i<bundles.size(); ++i) {
            final ForumTopicBundle topicBundle = (ForumTopicBundle) bundles.get(i);
            _forumTopics.add(ForumTopic.fromBundle(topicBundle));
        }
    }

    public void loadForumTopicPosts(final ForumTopicPostsBundle forumTopicPostsBundle) {
        final Integer forumTopicId = Util.parseInt(forumTopicPostsBundle.get(ForumTopicPostsBundle.Keys.TOPIC_ID));

        ForumTopic forumTopic = null;
        for (ForumTopic f : _forumTopics) {
            if (f.topicId.equals(forumTopicId)) {
                forumTopic = f;
                break;
            }
        }
        if (forumTopic == null) {
            return;
        }

        forumTopic.posts.clear();

        final List<Bundle> bundles = forumTopicPostsBundle.getGroup(ForumTopicPostsBundle.Keys.POSTS);
        if (bundles != null) {
            for (Integer i = 0; i < bundles.size(); ++i) {
                final ForumTopicPostBundle postBundle = (ForumTopicPostBundle) bundles.get(i);
                forumTopic.posts.add(ForumTopicPost.fromBundle(postBundle));
            }
        }
    }

    public List<ForumTopic> getForumTopics() {
        List<ForumTopic> forumTopics = new ArrayList<ForumTopic>();
        for (final ForumTopic forumTopic : _forumTopics) {
            forumTopics.add(forumTopic);
        }

        return forumTopics;
    }
}
