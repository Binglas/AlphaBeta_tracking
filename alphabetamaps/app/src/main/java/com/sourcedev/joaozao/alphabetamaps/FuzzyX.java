package com.sourcedev.joaozao.alphabetamaps;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.imex.FldExporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
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

    public void airConditionerFuzzy() {
        Engine engine = new Engine();
        engine.setName("AirConditioner");

        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("RoomTemperature");
        inputVariable.setRange(0, 40);
        inputVariable.addTerm(new Triangle("VERYCOLD", 0, 0, 10));
        inputVariable.addTerm(new Triangle("COLD", 0, 10, 20));
        inputVariable.addTerm(new Triangle("WARM", 10, 20, 30));
        inputVariable.addTerm(new Triangle("HOT", 20, 30, 40));
        inputVariable.addTerm(new Triangle("VERYHOT", 30, 40, 40));
        engine.addInputVariable(inputVariable);

        OutputVariable outputVariable = new OutputVariable();
        outputVariable.setEnabled(true);
        outputVariable.setName("Target");
        outputVariable.setRange(0.000, 1.000);
        outputVariable.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable.setDefuzzifier(new Centroid(200));
        outputVariable.setDefaultValue(Double.NaN);
        outputVariable.setLockPreviousOutputValue(false);
        outputVariable.setLockOutputValueInRange(false);
        outputVariable.addTerm(new Triangle("HEAT", 0.000, 0.250, 0.500));
        outputVariable.addTerm(new Triangle("NOCHANGE", 0.250, 0.500, 0.750));
        outputVariable.addTerm(new Triangle("COOL", 0.500, 0.750, 1.000));
        engine.addOutputVariable(outputVariable);

        //IF temperature=(Cold OR Very_Cold) AND target=Warm THEN  Action HEAT
        //IF temperature=(Hot OR Very_Hot) AND target=Warm THEN Action COOL
        //IF (temperature=Warm) AND (target=Warm) THEN Action NO_CHANGE

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if RoomTemperature is COLD or RoomTemperature is VERYCOLD then Target is HEAT", engine));
        ruleBlock.addRule(Rule.parse("if RoomTemperature is HOT or RoomTemperature is VERYHOT then Target is COOL", engine));
        ruleBlock.addRule(Rule.parse("if RoomTemperature is WARM then Target is NOCHANGE", engine));
        engine.addRuleBlock(ruleBlock);

        engine.configure("", "Maximum", "Minimum", "Maximum", "Centroid");

        StringBuilder status = new StringBuilder();
        if (!engine.isReady(status)) {
            throw new RuntimeException("Engine not ready. "
                    + "The following errors were encountered:\n" + status.toString());
        }

        for (int i = 0; i < 1000; ++i) {
            double input = inputVariable.getMinimum() + i * (inputVariable.range() / 1000);
            inputVariable.setInputValue(input);
            engine.process();
            FuzzyLite.logger().info(String.format(
                    "Ambient.input = %s -> Power.output = %s",
                    Op.str(input), Op.str(outputVariable.getOutputValue())));
            inputList.add((float) input);
            outputList.add((float) outputVariable.getOutputValue());
        }

        FuzzyLite.logger().info(new FldExporter().toString(engine));
    }

    public void fuzzySimpleDimmer() {
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

    public ArrayList<Float> getInputList() {
        return inputList;
    }

    public ArrayList<Float> getOutputList() {
        return outputList;
    }


}
