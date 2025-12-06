package alg;

import svm.SVM;
import io.*;

public class Adaline extends Algorithm{

	public Adaline(SVM svm){
		super(svm);
		if(svm.ind.V != null){
			name = "Adaline";
			svm.outd.algorithm = name;
			svm.outd.showInputData();
		}
	}
	
	public void run(){
		t = System.currentTimeMillis();
		boolean flag = false;
		float b = 0;
		int emax =1;
		float[] w = new float[N]; 
		
		for(long p = 1; p <= P; p++){
			double epm=0, db=0;
			for(int i = 1; i <= N; i++) {
				//w = alpha2ponders(alpha, b);
				float s = 0;
				for(int k = 0; k < dim; k++) s += w[k]*svm.ind.V[i].X[k];
				s += -b-svm.ind.V[i].cl.Y;
				//int y = s < 0 ? 0 : 1; 
				//int e = svm.ind.V[i].cl.Y - y;
				/*if(e != 0){
					erori = true;
					alpha[i]++;
					b = b - eta*e;
				}*/
				epm+=s*s;
				db+=s;
			}
			epm=epm/(2*N);
			db=-eta*db/N;
			if(epm>=emax){
				double[]dw = new double[N+1];
				for(int k=1;k<=N;k++) dw[k]=0;
				for(int k=1;k<=N;k++){
					for(int i=1;i<=N;i++){
						int s=0;
						for(int l = 0; l < dim; l++) s += w[l]*svm.ind.V[i].X[l];
						s += -b-svm.ind.V[i].cl.Y;
						dw[k]+=s*svm.ind.V[i].X[k];
					}
					dw[k]/=N; w[k]+=dw[k];
				}
				b+=db;
			}else {
				svm.outd.stages_count = p;
				svm.outd.computing_time = System.currentTimeMillis() - t;
				svm.outd.w = w;
				svm.outd.b = b;
				//svm.outd.w = alpha2ponders(alpha, b);
				svm.outd.accuracy = getAccuracy(svm.outd.w);
				svm.outd.showInputData();
				svm.outd.showOutputData();
				svm.design.calculates = false;
				svm.design.repaint();
				flag = true;
				break;
			}
		}
		if(!flag) 
			System.out.println(P + " stages have passed. Increase the number of stages and reloaded.");		
		else{		 
			for(long p = 1; p <= P; p++){
			double epm=0, db=0;
			for(int i = 1; i <= N; i++) {
				//w = alpha2ponders(alpha, b);
				float s = 0;
				for(int k = 0; k < dim; k++) s += w[k]*svm.ind.V[i].X[k];
				s += -b-svm.ind.V[i].cl.Y;
				//int y = s < 0 ? 0 : 1; 
				//int e = svm.ind.V[i].cl.Y - y;
				/*if(e != 0){
					erori = true;
					alpha[i]++;
					b = b - eta*e;
				}*/
				epm+=s*s;
				db+=s;
			}
			epm=epm/(2*N);
			db=-eta*db/N;
			if(epm>=emax){
				double[]dw = new double[N+1];
				for(int k=1;k<=N;k++) dw[k]=0;
				for(int k=1;k<=N;k++){
					for(int i=1;i<=N;i++){
						int s=0;
						for(int l = 0; l < dim; l++) s += w[l]*svm.ind.V[i].X[l];
						s += -b-svm.ind.V[i].cl.Y;
						dw[k]+=s*svm.ind.V[i].X[k];
					}
					dw[k]/=N; w[k]+=dw[k];
				}
				b+=db;
			}else {
				svm.outd.stages_count = p;
				svm.outd.computing_time = System.currentTimeMillis() - t;
				svm.outd.w = w;
				svm.outd.b = b;
				//svm.outd.w = alpha2ponders(alpha, b);
				svm.outd.accuracy = getAccuracy(svm.outd.w);
				svm.outd.showInputData();
				svm.outd.showOutputData();
				svm.design.calculates = false;
				svm.design.repaint();
				flag = true;
				break;
			}
		}
		svm.control.start.enable(false);		
	}
	/*
	public float[] alpha2ponders(int[] alpha, float b){
		float[] w = new float[dim+1]; 
		for(int k = 0; k < dim; k++) w[k] = 0;
		w[dim] = -b;
		for(int k = 0; k < dim; k++){
			for(int j = 0; j < N; j++){
				int y = svm.ind.V[j].cl.Y;
				if(y == 0) y = -1;
				w[k] += alpha[j]*y*svm.ind.V[j].X[k];
			}
		}
		
		return w;
	}*/
	
}
}