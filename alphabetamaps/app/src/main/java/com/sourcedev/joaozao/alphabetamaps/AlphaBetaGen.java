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
    //private double xk_1 = -8.598311, vk_1 = 0, a = 0.85, b = 0.005;
    // feup indoor
    private double xk_1 = -8.595512, vk_1 = 0, a = 0.85, b = 0.005;
    private double yk_1 = 41.177700, vyk_1 = 0;

    private double xk, vk, rk;
    private double yk, vyk, ryk;
    private double xm;
    private double ym;

    private Activity mContext;
    private GoogleMap mMap;
    private int countX = 0;
    private int countY = 0;

    private ArrayList<Double> arrayListX = new ArrayList<>();
    private ArrayList<Double> arrayListY = new ArrayList<>();


    public AlphaBetaGen(Activity pContext, GoogleMap pMap) {
        this.mContext = pContext;
        mMap = pMap;
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
                    Log.d(Defines.TAG,"lat = " + arrayListX.get(i) + "    long = " + arrayListY.get(i));
                    LatLng feup1 = new LatLng(arrayListY.get(i), arrayListX.get(i));
                    mMap.addMarker(new MarkerOptions().position(feup1));
                    Log.d(Defines.TAG, "feup latitude  : " + feup1.latitude + "feup longitude  : " + feup1.longitude);

                }
            }
        });

    }

    public void generateAlphaBetaX() {
        // indoor feup
        for(double i = -8.595512 ; i <= -8.594895 ; i = i + 0.00008) {
            {


                //Log.d("_DEBUG1" , "x seguinte : " + i);
                //xm = MIN + (float)(Math.random() * ((MAX - MIN) + 1));// input signal

                // indoor 41.178030   -8.595512
                // final  41.178008   -8.594895

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

                Log.d(Defines.TAG, " input = " + xm + " x = " + xk_1 + "    ArrayX(countX) = " + arrayListX.get(countX) + "   countX = " + countX);

                countX++;

            }
        }

    }

    public void generateAlphaBetaY() {
        for(double i = 41.178030 ; i <= 41.178123 ; i = i+0.00002) {
            {


                // y inicial  == 41.177700
                // incremento por iteracao  == 0.0002
                // condicao de paragem <= 41.178700

                // 41.178123, -8.595509

                ym = i;// input signal

                yk = yk_1 + ( vyk_1 * dt );
                vyk = vyk_1;

                ryk = ym - yk;

                yk += a * ryk;
                vyk += ( b * ryk ) / dt;

                yk_1 = yk;
                vyk_1 = vyk;

                arrayListY.add(yk_1);
                Log.d(Defines.TAG, " input = " + ym + " y = " + yk_1 + "    ArrayY(countY) = " + arrayListY.get(countY) + "   countY = " + countY);
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

