package com.example.Users.Types.Match;

public enum MatchStatuses {
        SCHEDULED(1), FINISHED(2);
        private int matchStatus;
        MatchStatuses(int i) {
            matchStatus = i;
        }
        public Integer getValue() {
            return this.matchStatus;
        }

}
