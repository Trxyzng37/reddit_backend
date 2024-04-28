package com.trxyzng.trung.comment.comment_id;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_id", schema = "UTILITIES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentID {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_generator")
    @SequenceGenerator(name="comment_id_generator", sequenceName = "seq_comment_id", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private int id;
}
