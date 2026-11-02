package app;

public class RecommendationEngine {
    public String recommendPosition(Player p) {
        double bmi = p.getBMI();
        if (p.getSpeed() >= 30 && p.getAgility() >= 7 && bmi < 25) {
            return "Winger";
        } else if (p.getSpeed() >= 27 && p.getAgility() >= 6) {
            return "Full-back";
        } else if (p.getAgility() >= 8) {
            return "Attacking midfielder";
        } else if (p.getHeight() >= 185) {
            return "Centre-back";
        } else {
            return "Central midfielder";
        }
    }

    public String[] generateTrainingPlan(Player p) {
        String position = recommendPosition(p);
        String[] plan = new String[5];
        plan[0] = "Day 1: Warm up and practise basic ball control.";

        if (position.equals("Winger")) {
            plan[1] = "Day 2: Practise dribbling and crossing.";
            plan[2] = "Day 3: Practise acceleration and movement.";
        } else if (position.equals("Full-back")) {
            plan[1] = "Day 2: Practise defending and positioning.";
            plan[2] = "Day 3: Practise running and crossing.";
        } else if (position.equals("Attacking midfielder")) {
            plan[1] = "Day 2: Practise passing and ball control.";
            plan[2] = "Day 3: Practise shooting and creating chances.";
        } else if (position.equals("Centre-back")) {
            plan[1] = "Day 2: Practise defensive positioning.";
            plan[2] = "Day 3: Practise passing from the back.";
        } else {
            plan[1] = "Day 2: Practise passing and movement.";
            plan[2] = "Day 3: Practise receiving and controlling the ball.";
        }

        plan[3] = "Day 4: Practise teamwork in a small-sided game.";
        plan[4] = "Day 5: Light practice and recovery.";
        return plan;
    }

    public String recommendDiet(Player p) {
        String category = p.getBMICategory();
        String position = recommendPosition(p);
        String advice;

        // These messages explain the program's category only.
        // They do not prescribe a diet or assess a teenager's health.
        if (category.equals("Below adult reference range")) {
            advice = "The calculated BMI is below the adult reference range. This result alone does not show whether nutrition is adequate. Individual nutrition needs should be discussed with a qualified health professional. ";
        } else if (category.equals("Within adult reference range")) {
            advice = "The calculated BMI is within the adult reference range. This does not determine individual nutrition needs. Regular balanced meals and enough fluids can support general wellbeing and football activity. ";
        } else if (category.equals("Above adult reference range")) {
            advice = "The calculated BMI is above the adult reference range. This result alone does not diagnose a health issue or determine what someone should eat. Individual guidance should come from a qualified health professional. ";
        } else {
            advice = "The calculated BMI is in the high adult reference range. This result alone is not a health assessment, especially for teenagers. Individual guidance should come from a qualified health professional. ";
        }

        if (position.equals("Winger") || position.equals("Full-back")) {
            advice = advice + "For this suggested position, regular meals containing carbohydrates and protein can help provide fuel for training and recovery. ";
        } else if (position.equals("Centre-back")) {
            advice = advice + "For this suggested position, varied balanced meals can support regular training and recovery. ";
        } else {
            advice = advice + "For this suggested position, balanced meals with a variety of foods can support regular football practice. ";
        }

        advice = advice + "The BMI categories in this app use adult reference bands for a programming example; they are not suitable for assessing teenagers.";
        return advice;
    }
}
