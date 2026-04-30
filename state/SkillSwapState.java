package SkillSwap.state;

import java.util.*;

import SkillSwap.domain.Offer;
import SkillSwap.domain.Request;
import SkillSwap.domain.Skill;
import SkillSwap.domain.Student;

public class SkillSwapState {
    public Map<String, Student> students = new HashMap<>();
    public Map<String, Skill> skills = new HashMap<>();
    public Map<String, Offer> offers = new HashMap<>();
    public Map<String, Request> requests = new HashMap<>();
}