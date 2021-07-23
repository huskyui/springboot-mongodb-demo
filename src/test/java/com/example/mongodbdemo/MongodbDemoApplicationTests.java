package com.example.mongodbdemo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@SpringBootTest
class MongodbDemoApplicationTests {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsert(){
        Person person = new Person();
        person.setId("1");
        person.setName("adios");
        person.setAge(11);
        mongoTemplate.insert(person);
    }



    @Test
    public void testInsertComplexData(){
        Query query = new Query()
                .addCriteria(where("_id").is(1));
        List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment_collection");
        if (!CollectionUtils.isEmpty(commentList)){
            Comment comment = commentList.get(0);
            List<Comment> subCommentList = comment.getSubComment();
            if (CollectionUtils.isEmpty(subCommentList)){
                subCommentList = new ArrayList<>();
            }

            Comment subComment = new Comment();
            subComment.setId(5);
            subComment.setComment("给第一条挽尊");
            subComment.setSubComment(null);
            subComment.setLevel(1);
            subComment.setUser("adios");
            subComment.setCreateTime(new Date());
            subCommentList.add(subComment);
            Update update = new Update();
            update.set("subCommentList",subCommentList);
            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "comment_collection");
            System.out.println(updateResult);


        }

    }

    private void delete() {
        Query query = new Query()
                .addCriteria(where("_id").is(3));
        DeleteResult deleteResult = mongoTemplate.remove(query, "comment_collection");
        System.out.println(deleteResult);
    }

    /**
     * 分页
     */
    private void pageList() {
        // 分页查询
        Query query = new Query()
                .addCriteria(where("_id").ne(1))
                .skip(0).limit(1);
        List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment_collection");
        System.out.println(commentList);
    }

    /**
     * 评论第二条
     */
    private void comment2() {
        Comment comment = new Comment();
        comment.setId(2);
        comment.setUser("adios");
        comment.setComment("二楼");
        comment.setLevel(0);
        comment.setLikeCount(0);
        comment.setCreateTime(new Date());
        Comment commentCallback = mongoTemplate.insert(comment, "comment_collection");
        System.out.println(commentCallback);
    }


    /**
     * 点赞评论
     */
    private void likeComment() {
        Query query = new Query()
                .addCriteria(where("_id").is(1));

        List<Comment> comments = mongoTemplate.find(query, Comment.class,"comment_collection");
        System.out.println(comments);
        if(!CollectionUtils.isEmpty(comments)){
            Comment comment = comments.get(0);
            List<String> likeUserList = comment.getLikeUserList();
            if(likeUserList == null){
                likeUserList = new ArrayList<>();
            }
            likeUserList.add("mysql");
            likeUserList.add("redis");
            comment.setLikeUserList(likeUserList);
            comment.setLikeCount(likeUserList.size());
            Update update = new Update();
            update.set("likeUserList",likeUserList);
            update.set("likeCount",likeUserList.size());
            UpdateResult commentCallback = mongoTemplate.updateFirst(query, update, "comment_collection");
            System.out.println(commentCallback);
        }
    }

    /**
     * 评论第一条
     */
    private void commentFirst() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setComment("大家好");
        comment.setUser("huskyui");
        List<String> likeUSerList = new ArrayList<>();
        likeUSerList.add("huskyui");
        likeUSerList.add("adios");
        comment.setLikeUserList(likeUSerList);
        comment.setLikeCount(likeUSerList.size());
        comment.setCreateTime(new Date());
        // 模拟第一次评论，在顶级评论列表中
        Comment commentCallback = mongoTemplate.insert(comment, "comment_collection");
        System.out.println(commentCallback);
    }


    @Test
    public void testFind(){
        Query query = new Query()
                .addCriteria(where("name").is("adios"));
        List<Person> people = mongoTemplate.find(query, Person.class);
        System.out.println(people);
    }

}
