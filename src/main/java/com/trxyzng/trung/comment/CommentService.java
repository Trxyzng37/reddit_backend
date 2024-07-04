package com.trxyzng.trung.comment;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.trxyzng.trung.comment.comment_id.CommentID;
import com.trxyzng.trung.comment.comment_id.CommnentIDRepository;
import com.trxyzng.trung.comment.pojo.CommentStatus;
import com.trxyzng.trung.post.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.trxyzng.trung.utility.SortCommentsList.sortByNew;
import static com.trxyzng.trung.utility.SortCommentsList.sortByVote;

@Service
public class CommentService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    public CommentService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Autowired
    CommnentIDRepository commnentIDRepository;
    @Autowired
    PostRepo postRepo;

    String collection_name = "check_comment_status";

    public Comment findCommentById(int post_id, int _id) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("_id").is(_id)
        ));
        return this.mongoTemplate.findOne(query, Comment.class, String.valueOf(post_id));
    }

    public ArrayList<Comment> getCommentsByUId(int uid, String sort) {
        int[] post_id_arr = postRepo.getAllPostsNotDeletedAndAllow();
        ArrayList<Integer> collection_id_arr = new ArrayList<>();
        ArrayList<Comment> results = new ArrayList<>();
        for(int i: post_id_arr) {
            if(this.mongoTemplate.collectionExists(String.valueOf(i))) {
                collection_id_arr.add(i);
            }
        }
        for(int i: collection_id_arr) {
            Query query = new Query();
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("uid").is(uid),
                    Criteria.where("deleted").is(false)
            ));
            List<Comment> comments = mongoTemplate.find(query, Comment.class, String.valueOf(i));
            for (Comment comment: comments) {
                results.add(comment);
            }
        }
        System.out.println(sort);

        if(sort.equals("new")) {
            sortByNew(results);
        }
        if(sort.equals("top_day")) {
            results = getCommentsByTop(results, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(1, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            sortByVote(results);
        }
        if(sort.equals("top_week")) {
            results = getCommentsByTop(results, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(7, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            sortByVote(results);

        }
        if(sort.equals("top_month")) {
            results = getCommentsByTop(results, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(31, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            sortByVote(results);

        }
        if(sort.equals("top_year")) {
            results = getCommentsByTop(results, Instant.now().truncatedTo(ChronoUnit.MILLIS).minus(365, ChronoUnit.DAYS), Instant.now().truncatedTo(ChronoUnit.MILLIS));
            sortByVote(results);

        }
        if(sort.equals("top_all_time")) {
            sortByVote(results);

        }
        return results;
    }

    private ArrayList<Comment> getCommentsByTop(ArrayList<Comment> comment_arr, Instant begin_day, Instant end_day) {
        ArrayList<Comment> results = new ArrayList<>();
        for(Comment comment: comment_arr) {
            if(comment.getCreated_at().isAfter(begin_day) && comment.getCreated_at().isBefore(end_day))
                results.add(comment);
        }
        return results;
    }

    public boolean isCommentByIdExist(int post_id, int parent_id) {
        BasicQuery query = new BasicQuery("{_id:" + parent_id + "}");
        List<Comment> commentList = this.mongoTemplate.find(query, Comment.class, Integer.toString(post_id));
        System.out.println("result list size "+commentList.size());
        if(commentList.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean isCommentByParentIdExist(int post_id, int parent_id) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("parent_id").is(parent_id)
        ));
        Comment comment = this.mongoTemplate.findOne(query, Comment.class, String.valueOf(post_id));
        return comment != null;
    }

    public int getCommentID() {
        return commnentIDRepository.save(new CommentID()).getId();
    }

    public Comment saveComment(Comment comment, int post_id) {
        if(comment.getUid() == 0) {
            return new Comment(0,0,0,0,"",Instant.now().truncatedTo(ChronoUnit.MILLIS),0,0,true);
        }
        else {
            return this.mongoTemplate.insert(comment, String.valueOf(post_id));
        }
    }

    public List<Comment> findAllCommentInCollection(String collection_name) {
        return this.mongoTemplate.findAll(Comment.class, collection_name);
    }

    public List<Comment> getCommentByParentIdAndLevel(int collection_id, int parent_id, int level) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("parent_id").is(parent_id),
                Criteria.where("level").is(level)
                ));
        query.with(Sort.by(Sort.Direction.ASC, "created_at"));
        return this.mongoTemplate.find(query, Comment.class, String.valueOf(collection_id));
    }

    public boolean isCollectionExist(int collection_id) {
        return this.mongoTemplate.collectionExists(String.valueOf(collection_id));
    }

    public int getMaxLevel(int collection_id) {
        if(this.isCollectionExist(collection_id)) {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "level"));
            List<Comment> found = this.mongoTemplate.find(query, Comment.class, String.valueOf(collection_id));
            if(found.size() == 0)
                return 0;
            else
                return found.get(0).getLevel();
        }
        return 0;
    }

    public List<Comment> getAllCommentsInOrder(int collection_id, int level, int max_level, int parent_id, List<Comment> resultList) {
                if (level == max_level+1)
                    return resultList;
                List<Comment> commentList = this.getCommentByParentIdAndLevel(collection_id, parent_id, level);
                int k = 0;
                while (k < commentList.size()) {
                    resultList.add(commentList.get(k));
                    int levell = commentList.get(k).getLevel();
                    getAllCommentsInOrder(collection_id, levell+1, max_level, commentList.get(k).get_id(), resultList);
                    k++;
                }
        return resultList;
    }

    public boolean updateCommentVote(int post_id, int _id, int newVote) {
        Query query = new Query();
        query.addCriteria(new Criteria().where("_id").is(_id));
        Update update = new Update().set("vote", newVote);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Comment.class, String.valueOf(post_id));
        return result.getMatchedCount() != 0;
    }

    public void saveOrUpdateCommentStatus(int _id, int uid, String voteType) {
        Query existQuery = new Query();
        existQuery.addCriteria(new Criteria().andOperator(
                Criteria.where("comment_id").is(_id),
                Criteria.where("uid").is(uid)
        ));
        boolean found = mongoTemplate.exists(existQuery, CommentStatus.class, "check_comment_status");
        if(found) {
            Query updateQuery = new Query();
            updateQuery.addCriteria(new Criteria().andOperator(
                    Criteria.where("comment_id").is(_id),
                    Criteria.where("uid").is(uid)
            ));
            Update update = new Update().set("vote_type", voteType);
            UpdateResult result = mongoTemplate.updateFirst(updateQuery, update, CommentStatus.class, "check_comment_status");
            System.out.println("update: "+result.getModifiedCount());
            System.out.println("update comment status: _id: "+_id+" uid: "+uid+" vote type: "+voteType);
        }
        else {
            CommentStatus commentStatus = new CommentStatus(_id, uid, voteType);
            CommentStatus c = this.mongoTemplate.insert(commentStatus, "check_comment_status");
            System.out.println("save: "+c);
            System.out.println("save comment status: _id: "+ c.getComment_id() + " uid: " + c.getUid() + " vote type: " + c.getVote_type());
        }
    }

    public CommentStatus findCommentStatus(int _id, int uid) {
        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("comment_id").is(_id),
                Criteria.where("uid").is(uid)
        ));
        return mongoTemplate.findOne(query, CommentStatus.class, "check_comment_status");
    }

    public boolean updateCommentContent(int post_id, int uid, int _id, String edit_content) {
        String collection_name = String.valueOf(post_id);
        Query updateQuery = new Query();
        updateQuery.addCriteria(new Criteria().andOperator(
                Criteria.where("_id").is(_id),
                Criteria.where("uid").is(uid)
        ));
        if(uid == 0) {
            return false;
        }
        Update update = new Update().set("content", edit_content);
        UpdateResult result = mongoTemplate.updateFirst(updateQuery, update, Comment.class, collection_name);
        System.out.println("update comment: "+result.getModifiedCount());
        return result.getMatchedCount() != 0;
    }

    public boolean deleteComment(int post_id, int _id) {
        boolean isChildCommentExist = isCommentByParentIdExist(post_id, _id);
        String collection_name = String.valueOf(post_id);
        if (isChildCommentExist) {
            Query updateQuery = new Query();
            updateQuery.addCriteria(new Criteria().andOperator(
                    Criteria.where("_id").is(_id)
            ));
            Update update = new Update()
                    .set("content", "<em style='font-weight: 300;'>Comment deleted</em>")
                    .set("deleted", true);
            UpdateResult result = mongoTemplate.updateFirst(updateQuery, update, Comment.class, collection_name);
            System.out.println("modify delete comment: "+result.getModifiedCount());
            return result.getModifiedCount() != 0;
        }
        else {
            Query deleteQuery = new Query();
            deleteQuery.addCriteria(new Criteria().andOperator(
                    Criteria.where("_id").is(_id)
            ));
            DeleteResult deleteResult = mongoTemplate.remove(deleteQuery, Comment.class, collection_name);
            System.out.println("delete comment: "+deleteResult.getDeletedCount());
            return deleteResult.getDeletedCount() != 0;
        }
    }

    public void deleteCommentsByPostId(int[] postId) {
        Query deleteQuery = new Query();
        for(int i: postId) {
            DeleteResult deleteResult = mongoTemplate.remove(deleteQuery, String.valueOf(i));
            System.out.println("delete comments for post_id: "+i);
        }
    }
}
