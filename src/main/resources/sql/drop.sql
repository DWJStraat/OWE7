-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2024-03-19 11:09:05.471

-- foreign keys
ALTER TABLE orfs_blast_results
    DROP FOREIGN KEY orfs_blast_results_blast_results;

ALTER TABLE orfs_blast_results
    DROP FOREIGN KEY orfs_blast_results_orfs;

ALTER TABLE orfs
    DROP FOREIGN KEY orfs_sequence;

-- tables
DROP TABLE blast_results;

DROP TABLE orfs;

DROP TABLE orfs_blast_results;

DROP TABLE sequence;

DROP TABLE test;

-- End of file.

