package SkillSwap.domain;
public class Score {
    private int score;

    public Score(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.score;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
        return this.score == other.score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("score=").append(score);
        sb.append('}');
        return sb.toString();
    }

}
