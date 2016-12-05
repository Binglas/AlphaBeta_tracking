package com.sourcedev.joaozao.alphabetamaps;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import java.util.ArrayList;

/**
 * Created by joaozao on 03/12/16.
 */

public class FuzzyX {


    private ArrayList<Float> inputList =  new ArrayList<>();
    private ArrayList<Float> outputList =  new ArrayList<>();

    public FuzzyX() {
    }

    public void fuzzy(){

        /*Engine engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable service= new InputVariable();
        service.setName("service");
        service.setRange(0.000, 1.000);
        service.addTerm(new Triangle("poor", 0.000, 0.250, 0.500));
        service.addTerm(new Triangle("good", 0.250, 0.500, 0.750));
        service.addTerm(new Triangle("excellent", 0.500, 0.750, 1.000));
        engine.addInputVariable(service);

        OutputVariable tip = new OutputVariable();
        tip.setName("Power");
        tip.setRange(0.000, 1.000);
        tip.setDefaultValue(Double.NaN);
        tip.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        tip.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        tip.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine.addOutputVariable(tip);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.addRule(Rule.parse("if service is poor then tip is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if service is good then tip is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if service is excellent then tip is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        engine.configure("", "", "Minimum", "Maximum", "Centroid");
        engine.setInputValue("service",ser);
        engine.process();
        output.setText(String.format("0.3f", engine.getOutputValue("Output")));*/
    }

    public void fuzzyExample() {
        Engine engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable ambient = new InputVariable();
        ambient.setName("Ambient");
        ambient.setRange(0.000, 1.000);
        ambient.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
        ambient.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        ambient.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
        engine.addInputVariable(ambient);

        OutputVariable power = new OutputVariable();
        power.setName("Power");
        power.setRange(0.000, 1.000);
        power.setDefaultValue(Double.NaN);
        power.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        power.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        power.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine.addOutputVariable(power);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        engine.configure("", "", "Minimum", "Maximum", "Centroid");

        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status)) {
            throw new RuntimeException("Engine not ready. "
                    + "The following errors were encountered:\n" + status.toString());
        }

        for (int i = 0; i < 1000; ++i) {
            double light = ambient.getMinimum() + i * (ambient.range() / 1000);
            ambient.setInputValue(light);
            engine.process();
            FuzzyLite.logger().info(String.format(
                    "Ambient.input = %s -> Power.output = %s",
                    Op.str(light), Op.str(power.getOutputValue())));
            inputList.add((float) light);
            outputList.add((float) power.getOutputValue());
        }
    }

    /*public void fuzzyExample() {
        Engine engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable ambient = new InputVariable();
        ambient.setName("Ambient");
        ambient.setRange(0, 1000);
        ambient.addTerm(new Triangle("DARK", 0, 250, 500));
        ambient.addTerm(new Triangle("MEDIUM", 250, 500, 750));
        ambient.addTerm(new Triangle("BRIGHT", 500, 750, 1000));
        engine.addInputVariable(ambient);

        OutputVariable power = new OutputVariable();
        power.setName("Power");
        power.setRange(0, 1000);
        power.setDefaultValue(Double.NaN);
        power.addTerm(new Triangle("LOW", 0, 250, 500));
        power.addTerm(new Triangle("MEDIUM", 250, 500, 750));
        power.addTerm(new Triangle("HIGH", 500, 750, 1000));
        engine.addOutputVariable(power);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        engine.configure("", "", "Minimum", "Maximum", "Centroid");

        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status)) {
            throw new RuntimeException("Engine not ready. "
                    + "The following errors were encountered:\n" + status.toString());
        }

        for (int i = 0; i < 50; ++i) {
            double light = ambient.getMinimum() + i * (ambient.range() / 50);
            ambient.setInputValue(light);
            engine.process();
            FuzzyLite.logger().info(String.format(
                    "Ambient.input = %s -> Power.output = %s",
                    Op.str(light), Op.str(power.getOutputValue())));
            inputList.add((float) light);
            outputList.add((float) power.getOutputValue());
        }
    }*/

    public ArrayList<Float> getInputList() {
        return inputList;
    }

    public ArrayList<Float> getOutputList() {
        return outputList;
    }
}
