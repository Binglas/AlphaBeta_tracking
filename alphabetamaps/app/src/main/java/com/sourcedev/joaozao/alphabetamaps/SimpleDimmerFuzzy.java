package com.sourcedev.joaozao.alphabetamaps;

/**
 * Created by joaozao on 05/12/16.
 */

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.defuzzifier.MeanOfMaximum;
import com.fuzzylite.imex.FldExporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Cosine;
import com.fuzzylite.term.Discrete;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class SimpleDimmerFuzzy {

    private Engine engine;

    public Engine getEngine1() {
        return engine1;
    }

    private Engine engine1;

    public SimpleDimmerFuzzy() {

    }

    public Engine getEngine() {
        return engine;
    }

    //Method 1: Set up the engine manually.
    public void fuzzy1() {
        engine = new Engine();
        engine.setName("simple-dimmer");

        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("Ambient");
        inputVariable.setRange(0.000, 1.000);
        inputVariable.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
        inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        inputVariable.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
        engine.addInputVariable(inputVariable);

        OutputVariable outputVariable = new OutputVariable();
        outputVariable.setEnabled(true);
        outputVariable.setName("Power");
        outputVariable.setRange(0.000, 1.000);
        outputVariable.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable.setDefuzzifier(new Centroid(200));
        outputVariable.setDefaultValue(Double.NaN);
        outputVariable.setLockPreviousOutputValue(false);
        outputVariable.setLockOutputValueInRange(false);
        outputVariable.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        outputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        outputVariable.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine.addOutputVariable(outputVariable);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine));
    }



    public void fuzzy3() {

        engine = new Engine();
        engine.setName("Laundry");

        InputVariable inputVariable1 = new InputVariable();
        inputVariable1.setEnabled(true);
        inputVariable1.setName("Load");
        inputVariable1.setRange(0.000, 6.000);
        inputVariable1.addTerm(Discrete.create("small", 0.000, 1.000, 1.000, 1.000, 2.000, 0.800, 5.000, 0.000));
        inputVariable1.addTerm(Discrete.create("normal", 3.000, 0.000, 4.000, 1.000, 6.000, 0.000));
        engine.addInputVariable(inputVariable1);

        InputVariable inputVariable2 = new InputVariable();
        inputVariable2.setEnabled(true);
        inputVariable2.setName("Dirt");
        inputVariable2.setRange(0.000, 6.000);
        inputVariable2.addTerm(Discrete.create("low", 0.000, 1.000, 2.000, 0.800, 5.000, 0.000));
        inputVariable2.addTerm(Discrete.create("high", 1.000, 0.000, 2.000, 0.200, 4.000, 0.800, 6.000, 1.000));
        engine.addInputVariable(inputVariable2);

        OutputVariable outputVariable1 = new OutputVariable();
        outputVariable1.setEnabled(true);
        outputVariable1.setName("Detergent");
        outputVariable1.setRange(0.000, 80.000);
        outputVariable1.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable1.setDefuzzifier(new MeanOfMaximum(500));
        outputVariable1.setDefaultValue(Double.NaN);
        outputVariable1.setLockPreviousOutputValue(false);
        outputVariable1.setLockOutputValueInRange(false);
        outputVariable1.addTerm(Discrete.create("less", 10.000, 0.000, 40.000, 1.000, 50.000, 0.000));
        outputVariable1.addTerm(Discrete.create("normal", 40.000, 0.000, 50.000, 1.000, 60.000, 1.000, 80.000, 0.000));
        outputVariable1.addTerm(Discrete.create("more", 50.000, 0.000, 80.000, 1.000));
        engine.addOutputVariable(outputVariable1);

        OutputVariable outputVariable2 = new OutputVariable();
        outputVariable2.setEnabled(true);
        outputVariable2.setName("Cycle");
        outputVariable2.setRange(0.000, 20.000);
        outputVariable2.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable2.setDefuzzifier(new MeanOfMaximum(500));
        outputVariable2.setDefaultValue(Double.NaN);
        outputVariable2.setLockPreviousOutputValue(false);
        outputVariable2.setLockOutputValueInRange(false);
        outputVariable2.addTerm(Discrete.create("short", 0.000, 1.000, 10.000, 1.000, 20.000, 0.000));
        outputVariable2.addTerm(Discrete.create("long", 10.000, 0.000, 20.000, 1.000));
        engine.addOutputVariable(outputVariable2);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(new Minimum());
        ruleBlock.setDisjunction(new Maximum());
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if Load is small and Dirt is not high then Detergent is less", engine));
        ruleBlock.addRule(Rule.parse("if Load is small and Dirt is high then  Detergent is normal", engine));
        ruleBlock.addRule(Rule.parse("if Load is normal and Dirt is low then Detergent is less", engine));
        ruleBlock.addRule(Rule.parse("if Load is normal and Dirt is high then Detergent is more", engine));
        ruleBlock.addRule(Rule.parse("if Detergent is normal or Detergent is less then Cycle is short", engine));
        ruleBlock.addRule(Rule.parse("if Detergent is more then Cycle is long", engine));
        engine.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine));

    }

    public void fuzzy4(){
        engine1 = new Engine();
        engine1.setName("simple-dimmer");

        InputVariable inputVariable = new InputVariable();
        inputVariable.setEnabled(true);
        inputVariable.setName("Ambient");
        inputVariable.setRange(0.000, 1.000);
        inputVariable.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500));
        inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        inputVariable.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000));
        engine1.addInputVariable(inputVariable);

        OutputVariable outputVariable1 = new OutputVariable();
        outputVariable1.setEnabled(true);
        outputVariable1.setName("Power");
        outputVariable1.setRange(0.000, 1.000);
        outputVariable1.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable1.setDefuzzifier(new Centroid(200));
        outputVariable1.setDefaultValue(Double.NaN);
        outputVariable1.setLockPreviousOutputValue(false);
        outputVariable1.setLockOutputValueInRange(false);
        outputVariable1.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
        outputVariable1.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
        outputVariable1.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
        engine1.addOutputVariable(outputVariable1);

        OutputVariable outputVariable2 = new OutputVariable();
        outputVariable2.setEnabled(true);
        outputVariable2.setName("InversePower");
        outputVariable2.setRange(0.000, 1.000);
        outputVariable2.fuzzyOutput().setAccumulation(new Maximum());
        outputVariable2.setDefuzzifier(new Centroid(500));
        outputVariable2.setDefaultValue(Double.NaN);
        outputVariable2.setLockPreviousOutputValue(false);
        outputVariable2.setLockOutputValueInRange(false);
        outputVariable2.addTerm(new Cosine("LOW", 0.200, 0.500));
        outputVariable2.addTerm(new Cosine("MEDIUM", 0.500, 0.500));
        outputVariable2.addTerm(new Cosine("HIGH", 0.800, 0.500));
        engine1.addOutputVariable(outputVariable2);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.setEnabled(true);
        ruleBlock.setName("");
        ruleBlock.setConjunction(null);
        ruleBlock.setDisjunction(null);
        ruleBlock.setActivation(new Minimum());
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine1));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine1));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine1));
        ruleBlock.addRule(Rule.parse("if Power is LOW then InversePower is HIGH", engine1));
        ruleBlock.addRule(Rule.parse("if Power is MEDIUM then InversePower is MEDIUM", engine1));
        ruleBlock.addRule(Rule.parse("if Power is HIGH then InversePower is LOW", engine1));
        engine1.addRuleBlock(ruleBlock);

        FuzzyLite.logger().info(new FldExporter().toString(engine1));
    }

    public void run() {
        fuzzy1();
        //fuzzy2();
    }
}
