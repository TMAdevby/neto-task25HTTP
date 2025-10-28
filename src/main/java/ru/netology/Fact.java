package ru.netology;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fact {
        private final int id;
        private final String text;
        private final String type;
        private final String user;
        private final String upvotes;
        public Fact(
                @JsonProperty("id") int id,
                @JsonProperty("text") String text,
                @JsonProperty("type") String type,
                @JsonProperty("user") String user,
                @JsonProperty("user") String upvotes
        ) {
            this.id = id;
            this.text = text;
            this.type = type;
            this.user = user;
            this.upvotes = upvotes;
        }
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Fact{" +
                    "id=" + id +
                    ", text='" + text + '\'' +
                    ", type='" + type + '\'' +
                    ", user='" + user + '\'' +
                    ", upvotes='" + upvotes + '\'' +
                    '}';
        }
}
