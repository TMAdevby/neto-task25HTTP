package ru.netology;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fact {
        private final int id;
        private final String text;
        private final String type;
        private final String body;
        public Post(
                @JsonProperty("userId") int userId,
                @JsonProperty("id") int id,
                @JsonProperty("title") String title,
                @JsonProperty("body") String body
        ) {
            this.factId = userId;
            this.id = id;
            this.text = title;
            this.body = body;
        }
        public int getFactId() {
            return factId;
        }
        // ... все getters
        @Override
        public String toString() {
            return "Post" +
                    "\n userId=" + factId +
                    "\n id=" + id +
                    "\n title=" + text +
                    "\n body=" + body;
        }

}
