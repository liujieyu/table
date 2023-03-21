package com.example.table.util;

public class MathUtil {
    //标准断面水深流量计算函数
//b：渠底宽度，m：边坡系数（矩形时为0），h:水深，n:糙率，i:底坡。
    public static double standardstsection_cal(double b,double m,double n,double i,double h)
    {
        double Q = 0,A,X,R,Y;
        try
        {
            A=(b+m*h)*h;
            Y=b+2*h*(1+m*m);
            X=Math.pow(Y,1.0/2);
            R=A/X;
            Q=A*Math.pow(R,2.0/3)*Math.sqrt(i)/n;
        }catch (Exception e)
        {
            //System.out.println("数据输入有误，请核对相关数据");
            Q=-1;
        }
        finally
        {
            return Q;
        }
    }


    //巴歇尔槽水深流量计算函数
//h：水深；types：巴歇尔槽型号
    public static double parshall_cal(double h,double C,double N1)
    {
        double Q=0;
        //通过巴歇尔槽型号types查找ST_PARSHALL_FLUME表中C和N1字段，分别赋予C和N1变量；
        try {
            Q = C * Math.pow(h, N1);
        }catch (Exception e)
            {
                //System.out.println("数据输入有误，请核对相关数据");
                Q=-1;
            }finally {
            return Q;
        }
    }
    //水闸水深流量计算函数
//水闸类型：types，过流总宽度：B，闸门开度：a，闸前水深：H，闸后水深：hs，重力加速度按9.81计算
    public static double gate_cal(double B,double a,double H,double hs)
    {
        double Q=0;
        try
        {
            if(a/H>=0.65)    //堰流
            {
                Q=B*2.28*hs*Math.pow(1-hs/H,0.4)*0.385*Math.sqrt(19.62)*Math.pow(H,1.5)/H;
            }else   //孔流
            {
                Q=1.0*0.582*B*a*Math.sqrt(2*9.81*H);
            }
        }catch (Exception e)
        {
            //System.out.println("数据输入有误，请核对相关数据");
            Q=-1;
        }finally
        {
            return Q;
        }

    }
}
