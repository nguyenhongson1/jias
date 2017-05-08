package com.tammo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ronke on 27.04.2017.
 */
public class NeuralNetwork {

    /**
     * Creates a neuronal network
     * @param layers the ArrayList with the current layers of neurons
     */


    public ArrayList<ArrayList<Neuron>> layers = new ArrayList<ArrayList<Neuron>>();

    public NeuralNetwork(int numberOfInputNeurons,int numberOfOutputNeurons, ArrayList<Integer> numberOfNeuronsForEachHiddenLayer){

        /**
         * Contructor of the neural network
         * @param numberOfInputNeurons amount of wanted "input" neurons
         * @param numberOfOutputNeurons amount of wanted "output" neurons
         * @param numberOfNeuronsForEachHiddenLayer amount of wanted "hidden" layers and neurons per each
         */

        if(numberOfInputNeurons ==0 || numberOfOutputNeurons == 0 || numberOfNeuronsForEachHiddenLayer.size() == 0)
            return;

        //Generating layers
        for(int i = 0; i <= 1 + (numberOfNeuronsForEachHiddenLayer.size() - 1) + 1; i++)
            layers.add(i, new ArrayList<Neuron>());

        //Generating "input" neurons
        for(int i = 1; i <= numberOfInputNeurons; i++)
            layers.get(0).add(new Neuron());

        //Creating a new instance of Random
        Random rnd = new Random();

        //Generating "hidden" layers with the amount of wanted neurons per each
        for (int hl : numberOfNeuronsForEachHiddenLayer) {
            //Creating a new instance of Neuron
            Neuron n = new Neuron();
            for(int j = 2; j <= hl ; j++){
                //Giving "n" a random Double for "weight"
                n.weight = rnd.nextDouble() * 2 - 1;//rand.nextDouble() * (maxX - minX) + minX
                //Adding threshold
                n.bias = rnd.nextDouble() * 2 - 1;
                layers.get(j).add(n);//clone oder nicht?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }

        //Generating "output" neurons
        for(int i = 1; i <= numberOfOutputNeurons; i++)
            layers.get(layers.size()- 1 ).add(new Neuron());

        //Creating a new instance of Synapse
        Synapse s = new Synapse();

        //Generating the connections between every layer
        for(int i = 0; i < layers.size(); i++)
            for(int j = 0; j < layers.get(i).size(); j++)
                for(int k = 0; k < layers.get(i).size(); k++){
                    //Giving "s" a random Double for "weight"
                    s.weight = rnd.nextDouble() * 2 - 1;
                    //Adding a synapse to the synapsArrayList of a neuron
                    layers.get(i).get(j).synapseArrayList = new ArrayList<Synapse>();
                    layers.get(i).get(j).synapseArrayList.add(s);
                }

        evolve();

    }



    public void setInput(ArrayList<Double> inputs){

        /**
         * Sets the weight of the "input" neurons
         * @param inputs the weight of the "input" neurons
         */

        if(inputs.size() != layers.get(layers.size() - 1).size())
            return;
        for(int i = 0; i < layers.get(0).size(); i++)
            layers.get(0).get(i).weight = inputs.get(i);
    }

    public ArrayList<Double> calculate() {

        /**
         * Calculates the weight of the "output" neurons
         */

        //NOTE: need to add query if the input was set

        ArrayList<Double> back = new ArrayList<Double>();

        for (int i = 1; i <= layers.size(); i++)
            for (int j = 0; j < layers.get(i).size(); j++) {
                for (int k = 0; k < layers.get(i).size(); k++)
                    //Neuron2 += Neuron1 * Synapse
                    layers.get(i).get(j).weight += layers.get(i - 1).get(k).weight * layers.get(i).get(j).synapseArrayList.get(k).weight;
                layers.get(i).get(j).weight = sigmoid(layers.get(i).get(j).weight + layers.get(i).get(j).bias);
            }
        for(Neuron n : layers.get(layers.size() - 1))
            back.add(n.weight);

        return back;
    }

    public void evolve(){
        /**
         * Lets the neuronal network evolve
         */
    }

    public void mutate(){
        /**
         * Lets the neuronal network mutate
         */
    }

    private Double sigmoid(double weight) {
        return (1 / (1 + Math.exp(-weight)));
    }
    
}

class Neuron {

    /**
     * Creates a Neuron
     * @param weight the current weight of the neuron
     * @param bias the threshold of the neuron
     * @param synapseArrayList the synapses which connects the neuron to the ones in the layer before
     */

    public Double weight = 0d;
    public Double bias = 0d;
    public ArrayList<Synapse> synapseArrayList;

}

class Synapse {

    /**
     * Creates a Synapse
     * @param weight the current weight of the synapse
     */

    public Double weight = 0d;
}

