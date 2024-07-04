package com.trxyzng.trung.utility;

import com.trxyzng.trung.comment.Comment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortCommentsList {
    public static void sortByNew(ArrayList<Comment> list) {
        Collections.sort(list, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                Instant instant1 = o1.getCreated_at();
                Instant instant2 = o2.getCreated_at();
                return instant2.compareTo(instant1);
            }
        });
    }

    public static void sortByVote(ArrayList<Comment> list) {
        Collections.sort(list, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                int v1 = o1.getVote();
                int v2 = o2.getVote();
                return Integer.compare(v2, v1);
            }
        });
    }
}
