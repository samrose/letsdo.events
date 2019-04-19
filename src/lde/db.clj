(ns lde.db
  (:require [clojure.java.jdbc :as jdbc]))

(defn create-db [path]
  {:dbtype "h2" :dbname path})

(def db (create-db "./db"))

(def table-users
  "create table if not exists users (
    id                identity,
    uuid              uuid default uuid(),
    name              varchar(255),
    email             varchar_ignorecase(255) unique,
    link              varchar(1023),
    created           timestamp not null default current_timestamp,
    password          varchar(1023),
    password_updated  timestamp not null default current_timestamp,
  )")

(defn create-user [data]
  (jdbc/insert! db "users" data))

(comment
  (create-user {"email" "abCd@de.com"})
  (create-user {:password nil})
  (->> (jdbc/query db "select * from users") (map :email))
  (jdbc/query db "show tables")
  (jdbc/query db "show columns from users")
  (jdbc/execute! db table-users)
  (jdbc/execute! db "drop table users")
)