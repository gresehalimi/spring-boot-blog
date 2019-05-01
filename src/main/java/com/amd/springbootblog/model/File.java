package com.amd.springbootblog.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tbl_file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", length = 500, nullable = false)
    private String fileName;

    @Column(name = "extension", length = 10, nullable = false)
    private String extension;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    public File(String fileName, String extension, byte[] file) {
        this.fileName = fileName;
        this.extension = extension;
        this.file = file;
    }
}