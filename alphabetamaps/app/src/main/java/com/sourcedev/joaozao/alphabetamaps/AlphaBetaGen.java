package com.sourcedev.joaozao.alphabetamaps;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by joaozao on 02/12/16.
 */

public class AlphaBetaGen {

    private double dt = 0.5;
    private double xk_1 = -8.598311, vk_1 = 0, a = 0.85, b = 0.005;
    private double yk_1 = 41.177700, vyk_1 = 0;

    private double xk, vk, rk;
    private double yk, vyk, ryk;
    private double xm;
    private double ym;

    private Activity mContext;
    private GoogleMap mMap;
    private int MIN = 1;
    private int MAX = 99;
    private int countX = 0;
    private int countY = 0;

    private ArrayList<Double> arrayListX = new ArrayList<>();
    private ArrayList<Double> arrayListY = new ArrayList<>();


    public AlphaBetaGen(Activity pContext, GoogleMap pMap, int MIN, int MAX) {
        this.mContext = pContext;
        mMap = pMap;
        this.MIN = MIN;
        this.MAX = MAX;
    }


    public void executeAlphaBetaGen() {
        generateAlphaBetaX();
        generateAlphaBetaY();
        if (mMap != null) {
            addMarkersToMap();
        }
    }

    private void addMarkersToMap() {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < arrayListY.size() ; i++) {
                    Log.d("_OLA","lat = " + arrayListX.get(i) + "    long = " + arrayListY.get(i));
                    LatLng feup1 = new LatLng(arrayListY.get(i), arrayListX.get(i));
                    mMap.addMarker(new MarkerOptions().position(feup1));
                    Log.d("_OLE", "feup latitude  : " + feup1.latitude + "feup longitude  : " + feup1.longitude);

                }
            }
        });

    }

    public void generateAlphaBetaX() {
        for(double i = -8.598311 ; i <= -8.594150 ; i = i + 0.0008) {
            {


                //Log.d("_DEBUG1" , "x seguinte : " + i);
                //xm = MIN + (float)(Math.random() * ((MAX - MIN) + 1));// input signal

                // x inicial  == -8.594311
                // incremento por iteracao  == 0.0002
                // condicao de paragem <= -8.594150

                xm = i; // input signal  Xo(K)

                // Recursively updating the values of the position and velocity
                xk = xk_1 + ( vk_1 * dt );
                vk = vk_1;

                // (Xo(k) - Xp(k))
                rk = xm - xk;

                // calculate new values for position and velocity
                xk += a * rk;
                vk += ( b * rk ) / dt;

                xk_1 = xk;
                vk_1 = vk;

                arrayListX.add(xk_1);

                Log.d("_DEBUG ", " input = " + xm + " x = " + xk_1 + "    ArrayX(countX) = " + arrayListX.get(countX) + "   countX = " + countX);

                countX++;

            }
        }

    }

    public void generateAlphaBetaY() {
        for(double i = 41.177700 ; i <= 41.178700 ; i = i+0.0002) {
            {


                // y inicial  == 41.177700
                // incremento por iteracao  == 0.0002
                // condicao de paragem <= 41.178700

                ym = i;// input signal

                yk = yk_1 + ( vyk_1 * dt );
                vyk = vyk_1;

                ryk = ym - yk;

                yk += a * ryk;
                vyk += ( b * ryk ) / dt;

                yk_1 = yk;
                vyk_1 = vyk;

                arrayListY.add(yk_1);
                Log.d("_DEBUG ", " input = " + ym + " y = " + yk_1 + "    ArrayY(countY) = " + arrayListY.get(countY) + "   countY = " + countY);
                countY++;
            }
        }

    }


    public ArrayList<Double> getArrayListY() {
        return arrayListY;
    }

    public ArrayList<Double> getArrayListX() {
        return arrayListX;
    }
}

