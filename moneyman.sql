DROP SCHEMA IF EXISTS moneyman CASCADE;
CREATE SCHEMA IF NOT EXISTS moneyman;



DROP TABLE IF EXISTS pipelines;
CREATE TABLE pipelines (
  execution_id      BIGSERIAL PRIMARY KEY ,
  name    VARCHAR(40)  NOT NULL,
  start_time VARCHAR(100),
  end_time VARCHAR(100),
  status VARCHAR(20) DEFAULT 'PENDING',
  description VARCHAR(40)  NOT NULL
);

DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  id      BIGSERIAL PRIMARY KEY ,
  name    VARCHAR(40) NOT NULL,
  description VARCHAR(40)  NOT NULL,
  status    VARCHAR(20) DEFAULT 'PENDING',
  start_time VARCHAR(100),
  end_time VARCHAR(100),
  action   VARCHAR(100) NOT NULL,
  pipeline INTEGER NOT NULL REFERENCES pipelines(execution_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE

);
DROP TABLE IF EXISTS transitions;
CREATE TABLE transitions (
  id     BIGSERIAL PRIMARY KEY,
  task   INTEGER NOT NULL REFERENCES tasks(id)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  transition  VARCHAR(60)
);


