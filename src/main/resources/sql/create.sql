-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-03-14 13:48:23.89

-- tables
-- Table: blast_results
CREATE TABLE blast_results (
                               result_id int  NOT NULL,
                               bit_score float  NOT NULL,
                               score int  NOT NULL,
                               eval text  NOT NULL,
                               identity int  NOT NULL,
                               positives int  NOT NULL,
                               gaps int  NOT NULL,
                               length int  NOT NULL,
                               accession int  NOT NULL,
                               CONSTRAINT blast_results_pk PRIMARY KEY (result_id)
);

-- Table: orfs
CREATE TABLE orfs (
                      orf_id int  NOT NULL,
                      start_pos int  NOT NULL,
                      end_pos int  NOT NULL,
                      strandedness Char  NOT NULL,
                      seq_id int  NOT NULL,
                      hash int  NOT NULL,
                      sequence_seq_id int  NOT NULL,
                      CONSTRAINT orfs_pk PRIMARY KEY (orf_id)
);

-- Table: orfs_blast_results
CREATE TABLE orfs_blast_results (
                                    orfs_orf_id int  NOT NULL,
                                    blast_results_result_id int  NOT NULL,
                                    CONSTRAINT orfs_blast_results_pk PRIMARY KEY (orfs_orf_id,blast_results_result_id)
);

-- Table: sequence
CREATE TABLE sequence (
                          seq_id int  NOT NULL,
                          sequence text  NOT NULL,
                          CONSTRAINT sequence_pk PRIMARY KEY (seq_id)
);

-- foreign keys
-- Reference: orfs_blast_results_blast_results (table: orfs_blast_results)
ALTER TABLE orfs_blast_results ADD CONSTRAINT orfs_blast_results_blast_results FOREIGN KEY orfs_blast_results_blast_results (blast_results_result_id)
    REFERENCES blast_results (result_id);

-- Reference: orfs_blast_results_orfs (table: orfs_blast_results)
ALTER TABLE orfs_blast_results ADD CONSTRAINT orfs_blast_results_orfs FOREIGN KEY orfs_blast_results_orfs (orfs_orf_id)
    REFERENCES orfs (orf_id);

-- Reference: orfs_sequence (table: orfs)
ALTER TABLE orfs ADD CONSTRAINT orfs_sequence FOREIGN KEY orfs_sequence (sequence_seq_id)
    REFERENCES sequence (seq_id);

-- End of file.

