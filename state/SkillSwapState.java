package state;

import domain.*;
import java.util.*;

public class SkillSwapState {
    public Map<String, Student> students = new HashMap<>();
    public Map<String, Skill> skills = new HashMap<>();
    public Map<String, Offer> offers = new HashMap<>();
    public Map<String, Request> requests = new HashMap<>();
}