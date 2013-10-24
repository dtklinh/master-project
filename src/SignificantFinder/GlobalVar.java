/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SignificantFinder;

import java.util.ArrayList;

/**
 *
 * @author Linh Dang
 */
public class GlobalVar {
    private ArrayList<String> mChrName;
    private ArrayList<String> mSeq;
    private ArrayList<Integer> mChIPScore;
    private ArrayList<Double> mMatchScore;
    private ArrayList<Double> mMyScore;
    private ArrayList<Double> mPvalue;
    private ArrayList<Integer> mBegin;
    private ArrayList<Integer> mEnd;
    private ArrayList<Integer> mOffset;
    private ArrayList<Integer> mOrientation;
    private double Thres;
    
    public void AddOffset(int i){
        mOffset.add(i);
    }
    public void AddOrientation(int i){
        mOrientation.add(i);
    }
    public void AddChrName(String chr){
        mChrName.add(chr);
    }
    public void AddBegin(int b){
        mBegin.add(b);
    }
    public void AddEnd(int e){
        mEnd.add(e);
    }
    public GlobalVar(){
        this.mChrName = new ArrayList<String>();
        this.mChIPScore = new ArrayList<Integer>();
        this.mMatchScore = new ArrayList<Double>();
        this.mMyScore = new ArrayList<Double>();
        this.mPvalue = new ArrayList<Double>();
        this.mSeq = new ArrayList<String>();
        this.mBegin = new ArrayList<Integer>();
        this.mEnd = new ArrayList<Integer>();
        this.mOffset = new ArrayList<Integer>();
        this.mOrientation = new ArrayList<Integer>();
        
    }
    public void AddSeq(String str){
        this.mSeq.add(str);
    }
    public void AddChIPScore(int i){
        this.mChIPScore.add(i);
    }
    public void AddMatchScore(double d){
        this.mMatchScore.add(d);
    }
    public void AddMyScore(double d){
        this.mMyScore.add(d);
    }
    public void AddMyScore(double[] d){
        int len = d.length;
        for(int i=0;i<len;i++){
            this.mMyScore.add(d[i]);
        }
    }
    public void AddPvalue(double d){
        this.mPvalue.add(d);
    }
    public void AddPvalue(double[] d){
        int len = d.length;
        for(int i=0;i<len;i++){
            this.mMyScore.add(d[i]);
        }
    }
    

    /**
     * @return the mSeq
     */
    public ArrayList<String> getmSeq() {
        return mSeq;
    }

    /**
     * @param mSeq the mSeq to set
     */
    public void setmSeq(ArrayList<String> mSeq) {
        this.mSeq = mSeq;
    }

    /**
     * @return the mChIPScore
     */
    public ArrayList<Integer> getmChIPScore() {
        return mChIPScore;
    }

    /**
     * @param mChIPScore the mChIPScore to set
     */
    public void setmChIPScore(ArrayList<Integer> mChIPScore) {
        this.mChIPScore = mChIPScore;
    }

    /**
     * @return the mMatchScore
     */
    public ArrayList<Double> getmMatchScore() {
        return mMatchScore;
    }

    /**
     * @param mMatchScore the mMatchScore to set
     */
    public void setmMatchScore(ArrayList<Double> mMatchScore) {
        this.mMatchScore = mMatchScore;
    }

    /**
     * @return the mMyScore
     */
    public ArrayList<Double> getmMyScore() {
        return mMyScore;
    }

    /**
     * @param mMyScore the mMyScore to set
     */
    public void setmMyScore(ArrayList<Double> mMyScore) {
        this.mMyScore = mMyScore;
    }

    /**
     * @return the mPvalue
     */
    public ArrayList<Double> getmPvalue() {
        return mPvalue;
    }

    /**
     * @param mPvalue the mPvalue to set
     */
    public void setmPvalue(ArrayList<Double> mPvalue) {
        this.mPvalue = mPvalue;
    }

    /**
     * @return the mBegin
     */
    public ArrayList<Integer> getmBegin() {
        return mBegin;
    }

    /**
     * @param mBegin the mBegin to set
     */
    public void setmBegin(ArrayList<Integer> mBegin) {
        this.mBegin = mBegin;
    }

    /**
     * @return the mEnd
     */
    public ArrayList<Integer> getmEnd() {
        return mEnd;
    }

    /**
     * @param mEnd the mEnd to set
     */
    public void setmEnd(ArrayList<Integer> mEnd) {
        this.mEnd = mEnd;
    }

    /**
     * @return the Thres
     */
    public double getThres() {
        return Thres;
    }

    /**
     * @param Thres the Thres to set
     */
    public void setThres(double Thres) {
        this.Thres = Thres;
    }

    /**
     * @return the mChrName
     */
    public ArrayList<String> getmChrName() {
        return mChrName;
    }

    /**
     * @param mChrName the mChrName to set
     */
    public void setmChrName(ArrayList<String> mChrName) {
        this.mChrName = mChrName;
    }

    /**
     * @return the mOffset
     */
    public ArrayList<Integer> getmOffset() {
        return mOffset;
    }

    /**
     * @param mOffset the mOffset to set
     */
    public void setmOffset(ArrayList<Integer> mOffset) {
        this.mOffset = mOffset;
    }

    /**
     * @return the mOrientation
     */
    public ArrayList<Integer> getmOrientation() {
        return mOrientation;
    }

    /**
     * @param mOrientation the mOrientation to set
     */
    public void setmOrientation(ArrayList<Integer> mOrientation) {
        this.mOrientation = mOrientation;
    }
    
}
