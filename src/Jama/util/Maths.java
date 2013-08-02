/*    */ package Jama.util;
/*    */ 
/*    */ public class Maths
/*    */ {
/*    */   public static double hypot(double paramDouble1, double paramDouble2)
/*    */   {
/*    */     double d;
/*  9 */     if (Math.abs(paramDouble1) > Math.abs(paramDouble2)) {
/* 10 */       d = paramDouble2 / paramDouble1;
/* 11 */       d = Math.abs(paramDouble1) * Math.sqrt(1.0D + d * d);
/* 12 */     } else if (paramDouble2 != 0.0D) {
/* 13 */       d = paramDouble1 / paramDouble2;
/* 14 */       d = Math.abs(paramDouble2) * Math.sqrt(1.0D + d * d);
/*    */     } else {
/* 16 */       d = 0.0D;
/*    */     }
/* 18 */     return d;
/*    */   }
/*    */ }

/* Location:           D:\Study\Java Code\getProteinName\lib\Jama-1.0.3.jar
 * Qualified Name:     Jama.util.Maths
 * JD-Core Version:    0.6.2
 */