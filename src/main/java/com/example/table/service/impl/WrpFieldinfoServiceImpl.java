package com.example.table.service.impl;

import com.example.table.entity.AlarmParam;
import com.example.table.entity.MedataParam;
import com.example.table.entity.WrpFieldinfo;
import com.example.table.mapper.WrpFieldinfoMapper;
import com.example.table.service.WrpFieldinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujieyu
 * @since 2019-09-20
 */
@Service
public class WrpFieldinfoServiceImpl extends ServiceImpl<WrpFieldinfoMapper, WrpFieldinfo> implements WrpFieldinfoService {
    @Autowired
    WrpFieldinfoMapper wrpFieldinfoMapper;
    //获取基础数据配置信息
    public List<Map<String,Object>> selectWrpFieldinfoByFormIDAndFieldID(String formid, String fieldid){
        return wrpFieldinfoMapper.selectWrpFieldinfoByFormIDAndFieldID(formid,fieldid);
    }
    //获取渠道站点对应渠道站点
    public List<Map<String,Object>> selectQudaoInfo(){
        return wrpFieldinfoMapper.selectQudaoInfo();
    }
    //获取渠道站点信息
    public List<Map<String,Object>> selectQudaoSiteInfo(MedataParam medataParam){
        if(medataParam.getMeCode()==null || medataParam.getMeCode().trim().equals("")){
            return wrpFieldinfoMapper.selectAllQdSiteInfo(medataParam);
        }else{
            if(medataParam.getMeCode().equals("Vother")){
                return wrpFieldinfoMapper.selectOtherQudaoSiteInfo(medataParam);
            }else{
                return  wrpFieldinfoMapper.selectQudaoSiteInfo(medataParam);
            }
        }

    }
    //表格选取表查询
    public List<Map<String,Object>> selectSysFormInfoByFCodeAndFNum(String fcode,int fnum){
        return wrpFieldinfoMapper.selectSysFormInfoByFCodeAndFNum(fcode,fnum);
    }
    //水量图数据
    public List<Map<String,Object>> selectBorchartData(AlarmParam alarmParam){
        return wrpFieldinfoMapper.selectBorchartData(alarmParam);
    }
    //饼状图数据
    public List<Map<String,Object>> selectPiechartData(){
        return wrpFieldinfoMapper.selectPiechartData();
    }
    //图像站点数据
    public List<Map<String,Object>> selectImgsiteData(){
        return wrpFieldinfoMapper.selectImgsiteData();
    }
    //渠道实时信息
    public List<Map<String,Object>> selectStCanalRBystcd(){
        return wrpFieldinfoMapper.selectStCanalRBystcd();
    }
    //闸阀实时信息
    public List<Map<String,Object>> selectStWasBystcd(){
        return wrpFieldinfoMapper.selectStWasBystcd();
    }
    //视频站点数据
    public List<Map<String,Object>> selectVideositeData(){
        return wrpFieldinfoMapper.selectVideositeData();
    }
    //雨情数据
    public List<Map<String,Object>> selectRainData(AlarmParam alarmParam){
        return wrpFieldinfoMapper.selectRainData(alarmParam);
    }
    //水位库容曲线
    public List<Map<String,Object>> selectWlStcpData(){
       // List<Map<String,Object>> totaldata=new ArrayList<>();
        List<Map<String,Object>> list=wrpFieldinfoMapper.selectWlStcpData();
       /* if(list==null || list.size()<2){

        }
        else if(list.size()>400){
            totaldata=list;
        }else{
            DecimalFormat gs=new DecimalFormat("0.000");
            Double sw1=((BigDecimal)list.get(0).get("WL")).doubleValue();
            Double sw2=((BigDecimal)list.get(list.size()-1).get("WL")).doubleValue();
            Double kr1=((BigDecimal)list.get(0).get("STCP")).doubleValue();
            Double kr2=((BigDecimal)list.get(list.size()-1).get("STCP")).doubleValue();
            int num=401;
            //求库容间距
            Double kr=(kr2-kr1)/(num-1);
            kr=Double.valueOf(gs.format(kr));

            for(int i=0;i<num;i++){
                Map<String,Object> temp=new HashMap<>();
                temp.put("STCP",kr1+kr*i);
                temp.put("WL",this.SWKRSelect(list, 2, (Double)temp.get("STCP")));
                totaldata.add(temp);
            }
        }
        return totaldata;*/
        return list;
    }
    //带条件水位库容曲线
    public List<Map<String,Object>> selectWlStcpDataByDate(AlarmParam alarmParam){
        List<Map<String,Object>> list=wrpFieldinfoMapper.selectWlStcpDataByDate(alarmParam);
        return list;
    }
    //求type为1求库容，为2求水位
    private Double SWKRSelect(List<Map<String,Object>> list,int type,Double x){
        Double sw1=new Double(0);
        Double sw2=new Double(0);
        Double sw3=new Double(0);
        Double kr1=new Double(0);
        Double kr2=new Double(0);
        Double kr3=new Double(0);

        Double y=null;

        if(type==1){
            for(int i=0;i<list.size();i++){
                Map<String,Object> obj=list.get(i);
                if(((BigDecimal) obj.get("WL")).doubleValue()<=x && ((BigDecimal) obj.get("WL")).doubleValue()>=sw1){
                    sw3=sw1;
                    kr3=kr1;
                    sw1=((BigDecimal) obj.get("WL")).doubleValue();
                    kr1=((BigDecimal)obj.get("STCP")).doubleValue();
                }
                if(((BigDecimal) obj.get("WL")).doubleValue()>=x && (((BigDecimal) obj.get("WL")).doubleValue()<=sw2 || sw2<x)){
                    sw3=sw2;
                    kr3=kr2;
                    sw2=((BigDecimal) obj.get("WL")).doubleValue();
                    kr2=((BigDecimal)obj.get("STCP")).doubleValue();
                }
            }
            if(sw1>sw2){
                sw2=sw1;
                sw1=sw3;
                kr2=kr1;
                kr1=kr3;
            }
            if(sw1-sw2<0.001 && sw1-sw2>-0.001) {
                y = kr1;
            }
            else {
                y=(x-sw1)*(kr2-kr1)/(sw2-sw1)+kr1;
            }
        }else if(type==2){
            for(int i=0;i<list.size();i++) {
                Map<String, Object> obj = list.get(i);
                if(((BigDecimal)obj.get("STCP")).doubleValue()<=x && ((BigDecimal)obj.get("STCP")).doubleValue()>=kr1){
                    sw3=sw1;
                    kr3=kr1;
                    sw1=((BigDecimal) obj.get("WL")).doubleValue();
                    kr1=((BigDecimal)obj.get("STCP")).doubleValue();
                }
                if(((BigDecimal)obj.get("STCP")).doubleValue()>=x && (((BigDecimal)obj.get("STCP")).doubleValue()<=kr2 || kr2<x)){
                    sw3=sw2;
                    kr3=kr2;
                    sw2=((BigDecimal) obj.get("WL")).doubleValue();
                    kr2=((BigDecimal)obj.get("STCP")).doubleValue();
                }
            }
            if(sw1>sw2){
                sw2=sw1;
                sw1=sw3;
                kr2=kr1;
                kr1=kr3;
            }
            if(kr2-kr1<0.001 && kr2-kr1>-0.001) {
                y = sw1;
            }
            else{
                y=(x-kr1)*(sw2-sw1)/(kr2-kr1)+sw1;
            }
        }
        return y;
    }
    //闸门开度流量数据
    public List<Map<String,Object>> selectGateData(AlarmParam alarmParam){
        return wrpFieldinfoMapper.selectGateData(alarmParam);
    }
    //今日雨量
    public List<Map<String,Object>> selectRainDateInfo(){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        int curhour=calendar.get(Calendar.HOUR_OF_DAY);
        if(curhour<8){
            calendar.add(Calendar.DAY_OF_MONTH,-1);
        }
        String date=fmt.format(calendar.getTime());
        return wrpFieldinfoMapper.selectRainDateInfo(date);
    }
    //实时河道水位
    public List<Map<String,Object>> selectRiverRData(){
        return wrpFieldinfoMapper.selectRiverRData();
    }
    //实时水库水位
    public List<Map<String,Object>> selectRsvrRData(){
        return wrpFieldinfoMapper.selectRsvrRData();
    }
    //今日雨量
    public List<Map<String,Object>> selectTodayRainData(){return wrpFieldinfoMapper.selectTodayRainData();}
    //入库出库流量和水量
    public List<Map<String,Object>> selectTodayQandW(){
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=new HashMap<String,Object>();
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);
        //入库流量 出库流量  入库水量  出库水量
        Double inq=0.00,outq=0.00,inw=0.00,outw=0.00;
        //入库小时水量，出库小时水量
        Double[] inwarray=new Double[hour+1];
        Double[] outarray=new Double[hour+1];
        for(int j=0;j<hour+1;j++){
            outarray[j]=0.0;
        }
        //导流洞闸门编码
        List<String> stcdlist=new ArrayList<String>();
        //生态、灌溉和溢洪洞流量及水量
        List<Map<String,Object>> listw=wrpFieldinfoMapper.selectStHCanalCByToday();
        for(int i=0;i<listw.size();i++){
            Map<String,Object> mapw=listw.get(i);
            Integer tm=(Integer)mapw.get("TM");
            double wq=((BigDecimal)mapw.get("WQ")).doubleValue();
            double qval=((BigDecimal)mapw.get("Q")).doubleValue();
            outarray[tm]+=wq/10000;
            if(tm==hour-1){
                outq+=qval;
            }
        }
        //判断闸门流量数据的日期是否今日更新
        List<Map<String,Object>> signlist=wrpFieldinfoMapper.selectTodayQSign();
        //日期未更新出库流量汇总
            Map<String,Object> signobj=signlist.get(0);
            Double otq=((BigDecimal)signobj.get("Q")).doubleValue();
            Integer sign=(Integer)signobj.get("SIGN");
            if(sign<0){
                String stcd=(String)signobj.get("STCD");
                stcdlist.add(stcd);
            }else{
                for(int i=0;i<hour;i++){
                    outarray[i]+=otq*3600/10000;
                    outw+=outarray[i];
                }
                outarray[hour]+=otq*(minute*60+second)/10000;
                outq+=otq;
                map.put("OTQ",String.format("%.3f", outq));
                map.put("ODW",String.format("%.3f", outw));
            }

        //设置出库流量和出库水量
        //如果日期均未更新
        if(stcdlist.size()>0){
                String stcdh="6530240018";
                Map<String,Object> zeroobj=wrpFieldinfoMapper.selectStGatageHByLastAndStcd(stcdh).get(0);
                double zeroq=((BigDecimal)zeroobj.get("Q")).doubleValue();
                List<Map<String,Object>> listh=wrpFieldinfoMapper.selectStGateHByTodayQStcd(stcdh);
                if(listh.size()==1 ){
                    Map<String, Object> hisobj = listh.get(0);
                    int curhour=Integer.parseInt(((String)hisobj.get("TM")).split(" ")[1]);
                    double Sumq = ((BigDecimal) hisobj.get("SUMQ")).doubleValue();
                    int Sumval = ((Integer) hisobj.get("SUMVAL")).intValue();
                    double afterlastq = ((BigDecimal) hisobj.get("LASTQ")).doubleValue();
                    for(int m=0;m<curhour;m++){
                        outarray[m]+=zeroq*3600/10000;
                    }
                    outarray[curhour]+=(Sumq+(3600-Sumval)*zeroq)/10000;
                    for(int m=curhour+1;m<=hour;m++){
                        outarray[m]+=afterlastq*3600/10000;
                    }
                }else {
                    for (int i = 0; i < listh.size() - 1; i++) {
                        Map<String, Object> hisobj = listh.get(i);
                        int curhour = Integer.parseInt(((String) hisobj.get("TM")).split(" ")[1]);
                        double Sumq = ((BigDecimal) hisobj.get("SUMQ")).doubleValue();
                        int Sumval = ((Integer) hisobj.get("SUMVAL")).intValue();
                        double curq = ((BigDecimal) hisobj.get("LASTQ")).doubleValue();
                        if (i == 0) {
                            for (int m = 0; m < curhour; m++) {
                                outarray[m] += zeroq * 3600 / 10000;
                            }
                            outarray[curhour] += (Sumq + (3600 - Sumval) * zeroq) / 10000;
                        }
                        Map<String, Object> afterobj = listh.get(i + 1);
                        int afterhour = Integer.parseInt(((String) afterobj.get("TM")).split(" ")[1]);
                        double afterSumq = ((BigDecimal) afterobj.get("SUMQ")).doubleValue();
                        int afterSumval = ((Integer) afterobj.get("SUMVAL")).intValue();
                        double afterlastq = ((BigDecimal) afterobj.get("LASTQ")).doubleValue();
                        for (int m = curhour + 1; m < afterhour; m++) {
                            outarray[m] += curq * 3600 / 10000;
                        }
                        outarray[afterhour] += (afterSumq + (3600 - afterSumval) * curq) / 10000;
                        if (i == listh.size() - 2) {
                            for (int m = afterhour + 1; m <= hour; m++) {
                                outarray[m] += afterlastq * 3600 / 10000;
                            }
                        }
                    /*if(i<listh.size()-2){

                    }else{
                        String btime=((String)afterobj.get("BEGINVAL")).split(" ")[1];
                        int tempsecond=Integer.parseInt(btime.split(":")[1])*60+Integer.parseInt(btime.split(":")[2]);
                        int tempSumval=afterSumval+tempsecond+1;
                        double tempSumq=afterSumq+tempsecond*curq;
                        if(tempSumval<3600){
                            outarray[afterhour]+=tempSumq/10000;
                        }else{
                            int temp=tempSumval/3600;
                            for(int n=0;n<temp;n++){
                                double outtemp=tempSumq/tempSumval*3600/10000;
                                outarray[afterhour+n]+=outtemp;
                            }
                            int tempy=tempSumval%3600;
                            outarray[afterhour+temp]+=tempSumq/tempSumval*tempy/10000;
                        }
                    }*/
                    }
                }
            for(int h=0;h<hour;h++){
                outw+=outarray[h];
            }
            outq=outarray[hour-1]*10000/3600;
            map.put("OTQ",String.format("%.3f", outq));
            map.put("ODW",String.format("%.3f", outw));
        }
        //设置入库流量和入库水量
        //获取今日入库流量站小时信息
        List<Map<String,Object>> riverlist=wrpFieldinfoMapper.selectStRiverHByToday();
        //获取水库小时库容增量信息
        List<Map<String,Object>> rsvrlist1=wrpFieldinfoMapper.selectStRsvrHByToday();
        List<Map<String,Object>> rsvrlist=new ArrayList<Map<String,Object>>();
        for(int i=0;i<rsvrlist1.size();i++){
            Map<String,Object> obj1=rsvrlist1.get(i);
            int tm=((Integer)obj1.get("TM")).intValue();
            if(i==0){
                if(tm>0){
                    for(int s=0;s<tm;s++){
                        Map<String,Object> obj=new HashMap<String,Object>();
                        obj.put("DT",obj1.get("DT"));
                        obj.put("TM",s);
                        obj.put("HW",new BigDecimal(0.00));
                        rsvrlist.add(obj);
                    }
                }
                rsvrlist.add(obj1);
            }else{
                Map<String,Object> obj0=rsvrlist1.get(i-1);
                int tm0=((Integer)obj0.get("TM")).intValue();
                if(tm-tm0>1){
                    for(int s=tm0+1;s<tm;s++){
                        Map<String,Object> obj=new HashMap<String,Object>();
                        obj.put("DT",obj1.get("DT"));
                        obj.put("TM",s);
                        obj.put("HW",new BigDecimal(0.00));
                        rsvrlist.add(obj);
                    }
                }
                rsvrlist.add(obj1);
            }
        }
        if(riverlist.size()<hour+1){
            Map<String,Object> lastobj=new HashMap<String,Object>();
            Map<String,Object> endobj=riverlist.get(riverlist.size()-1);
            lastobj.put("DT",endobj.get("DT"));
            lastobj.put("TM",hour);
            lastobj.put("RZ",endobj.get("RZ"));
            riverlist.add(lastobj);
        }
        if(rsvrlist.size()<hour+1){
            Map<String,Object> lastobj=new HashMap<String,Object>();
            Map<String,Object> endobj=rsvrlist.get(rsvrlist.size()-1);
            lastobj.put("DT",endobj.get("DT"));
            lastobj.put("TM",hour);
            lastobj.put("HW",endobj.get("HW"));
            rsvrlist.add(lastobj);
        }
        for(int n=0;n<riverlist.size();n++){
            Map<String,Object> riverobj=riverlist.get(n);
            Double rz=((BigDecimal)riverobj.get("RZ")).doubleValue();
            //未达到率定值
         //   if(rz<2710.95){
                Map<String,Object> krobj=rsvrlist.get(n);
                Double curinw=((BigDecimal)krobj.get("HW")).doubleValue()+outarray[n];
                if(curinw<0.00){
                    curinw=0.00;
                }
                inwarray[n]=curinw;
                if(n==riverlist.size()-2){
                    Double curinq=curinw*10000/3600;
                    inq=curinq;
                    if(inq<0.00){
                        inq=0.00;
                    }
                }
         //   }//使用率定
         //   else{
         //      Double curinq=Math.pow(rz/2710.7,1/0.00009);
         //        if(n==riverlist.size()-1){
         //           Double curinw=curinq*(minute*60+second)/10000;
         //           inwarray[n]=curinw;
         //       }else{
         //           if(n==riverlist.size()-2){
         //               inq=curinq;
         //           }
         //           Double curinw=curinq*3600/10000;
         //           inwarray[n]=curinw;
         //       }
         //   }
        }
        for(int h=0;h<hour;h++){
            inw+=inwarray[h];
        }
        map.put("INQ",String.format("%.3f", inq));
        map.put("IDW",String.format("%.3f", inw));
        list.add(map);
        return list;
    }
    //河道水位2小时变化值
    public List<Map<String,Object>> selectChangeRiverData(){
        return wrpFieldinfoMapper.selectChangeRiverData();
    }
    //水库水位2小时变化值
    public List<Map<String,Object>> selectChangeRsvrRData(){
        return wrpFieldinfoMapper.selectChangeRsvrRData();
    }
    //最新1小时EXCEL导出SQL
    public Map<String,Object> selectLastHourExcelData(){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        Map<String,Object> map=new HashMap<String,Object>();
        //当前水库库容和水位
        List<Map<String,Object>> list1=wrpFieldinfoMapper.selectLastSwAndKrHour();
        Map<String,Object> obj1=list1.get(0);
        double w=((BigDecimal)obj1.get("W")).doubleValue();
        double rz=((BigDecimal)obj1.get("RZ")).doubleValue();
        map.put("Value1",String.format("%.2f", w)+"万m³");
        map.put("Value2",String.format("%.2f", rz)+"m");
        map.put("Value17",String.format("%.2f", rz));
        //闸门开度信息
        List<Map<String,Object>> list2=wrpFieldinfoMapper.selectZmKdHourInfo();
        for(int i=0;i<list2.size();i++){
            Map<String,Object> gate=list2.get(i);
            String stcd=(String)gate.get("STCD");
            double od=((BigDecimal)gate.get("OD")).doubleValue();
            String tm=(String)gate.get("TM");
            if(stcd.equals("6530240017")){
                map.put("Value7",String.format("%.2f", od));
                map.put("Value8",tm);
            }
            if(stcd.equals("6530240018")){
                map.put("Value9",String.format("%.2f", od));
                map.put("Value10",tm);
            }
        }
        //雨量信息
        List<Map<String,Object>> list3=wrpFieldinfoMapper.selectSumRainHourInfo();
        for(int i=0;i<list3.size();i++){
            Map<String,Object> rain=list3.get(i);
            String stcd=(String)rain.get("STCD");
            String p=((BigDecimal)rain.get("P")).toString();
            if(stcd.equals("6530240001")){
                map.put("Value11",p);
            }
            if(stcd.equals("6530240002")){
                map.put("Value16",p);
            }
            if(stcd.equals("6530240003")){
                map.put("Value12",p);
            }
        }
        //总出库水量 出库流量
        double otsl=0.0,otq=0.0;
        //生态用水流量站 灌溉用水流量站  溢洪洞出库流量站
        List<Map<String,Object>> list4=wrpFieldinfoMapper.selectStllSiteHourInfo();
        //灌溉洞 流量和水量
        double ggdq=0,ggdwq=0;
        for(int i=0;i<list4.size();i++){
            Map<String,Object> stobj=list4.get(i);
            double z=((BigDecimal)stobj.get("Z")).doubleValue();
            double q=((BigDecimal)stobj.get("Q")).doubleValue();
            double wq=((BigDecimal)stobj.get("WQ")).doubleValue();
            String stcd=(String)stobj.get("STCD");
            if(stcd.equals("6530240004")){
                map.put("Value21",String.format("%.2f", z)+"(水深)");
                map.put("Value22",String.format("%.3f",q));
                map.put("Value23",String.format("%.3f",(wq/10000)));
                ggdq+=q;
                ggdwq+=wq/10000;
            }
            if(stcd.equals("6530240005")){
                map.put("Value18",String.format("%.2f", z)+"(水深)");
                map.put("Value19",String.format("%.3f",q));
                map.put("Value20",String.format("%.3f",(wq/10000)));
                ggdq+=q;
                ggdwq+=wq/10000;
            }
            if(stcd.equals("6530240019")){
                map.put("Value24",String.format("%.2f", z)+"(水深)");
                map.put("Value25",String.format("%.3f",q));
                map.put("Value26",String.format("%.3f",(wq/10000)));
            }
            if(stcd.equals("6530240020")){
                map.put("Value31",String.format("%.2f", z)+"(水深)");
            }
            otq+=q;
            otsl+=wq/10000;
        }
        //灌溉洞累计
        map.put("Value27",String.format("%.3f",ggdq));
        map.put("Value28",String.format("%.3f",ggdwq));
        //判断导流洞最新小时是否有出库流量更新
        List<Map<String,Object>> list7=wrpFieldinfoMapper.selectReflashHour();
        //导流洞是否有数据标识
        int dlsign=0;
            Map<String,Object> signobj=list7.get(0);
            Integer sign=(Integer)signobj.get("SIGN");
            String stcd="6530240018";
            double dlq=((BigDecimal)signobj.get("Q")).doubleValue();
            if(sign<0){
                dlsign=-1;
            }else{
                double dlsl=dlq*3600/10000;
                otq+=dlq;
                otsl+=dlsl;
                map.put("Value29",String.format("%.3f", dlq));
                map.put("Value30",String.format("%.3f", dlsl));
            }
            if(dlsign<0) {
                List<Map<String, Object>> list8 = wrpFieldinfoMapper.selectOutWAndSLHour("6530240018");
                double signq = 0, sl = 0;
                if (list8.size() == 1) {
                    Map<String, Object> onlyobj = list8.get(0);
                    int lasthour = Integer.parseInt(((String) onlyobj.get("TM")).split(" ")[1]);
                    if (lasthour == hour - 2) {
                        signq = ((BigDecimal) onlyobj.get("LASTQ")).doubleValue();
                        sl = signq * 3600 / 10000;
                    } else {
                        Map<String, Object> bobj2 = wrpFieldinfoMapper.selectBeforeTwoHourData(stcd).get(0);
                        double lastq = ((BigDecimal) bobj2.get("Q")).doubleValue();
                        double sumq = ((BigDecimal) onlyobj.get("SUMQ")).doubleValue();
                        int sumval = (Integer) onlyobj.get("SUMVAL");
                        sl = (sumq + (3600 - sumval) * lastq) / 10000;
                        signq = sl * 10000 / 3600;
                    }
                } else {
                    Map<String, Object> lastobj = list8.get(0);
                    Map<String, Object> curobj = list8.get(1);
                    double lastq = ((BigDecimal) lastobj.get("LASTQ")).doubleValue();
                    double sumq = ((BigDecimal) curobj.get("SUMQ")).doubleValue();
                    int sumval = (Integer) curobj.get("SUMVAL");
                    sl = (sumq + (3600 - sumval) * lastq) / 10000;
                    signq = sl * 10000 / 3600;
                }
                otq+=signq;
                otsl += sl;
                map.put("Value29", String.format("%.3f", signq));
                map.put("Value30", String.format("%.3f", sl));
            }
        //出库流量  出库水量
        map.put("Value5",String.format("%.3f", otq)+"m³/s");
        map.put("Value6",String.format("%.3f", otsl)+"万m³");
        //入库流量  入库水量
        double curinq=0.0,curinsl=0.0;
        //入库流量站  水位
        List<Map<String,Object>> list5=wrpFieldinfoMapper.selectInSiteHourSw();
        double rksw=((BigDecimal)list5.get(0).get("RZ")).doubleValue();
        map.put("Value13",String.format("%.2f", rksw));
        //未达到率定值
      //  if(rksw<2710.95){
            //获取小时库容增量
            List<Map<String,Object>> list6=wrpFieldinfoMapper.selectKwAddHour();
            double kradd=((BigDecimal)list6.get(0).get("HW")).doubleValue();
            curinsl=kradd+otsl;
            if(curinsl<0.0){
                curinsl=0.0;
            }
            curinq=curinsl*10000/3600;
    /*    }//使用率定
        else{
            curinq=Math.pow(rksw/2710.7,1/0.00009);
            curinsl=curinq*3600/10000;
        }*/
        map.put("Value14",String.format("%.3f", curinq));
        map.put("Value15",String.format("%.3f", curinsl));
        map.put("Value4",String.format("%.3f", curinq)+"m³/s");
        map.put("Value3",String.format("%.3f", curinsl)+"万m³");
        return map;
    }
    //水库小时表信息
    public Map<String,Object> selectStRsvrHByHours(AlarmParam alarmparam){
        List<Map<String,Object>> list=wrpFieldinfoMapper.selectStRsvrHByHours(alarmparam);
        for(int i=list.size()-1;i>=0;i--){
            Map<String, Object> tempobj=list.get(i);
            if(tempobj.get("TEMP")==null){
                if(i==list.size()-1){
                    String dt=tempobj.get("DT").toString();
                    String tempdt=dt+":00:00";
                    AlarmParam tempara=new AlarmParam();
                    tempara.setBegintime(tempdt);
                    Map<String, Object>  mapobj=selectStRsvrHByBefore(alarmparam).get(0);
                    tempobj.put("RZ",mapobj.get("RZ"));
                    tempobj.put("HW",mapobj.get("HW"));
                    tempobj.put("KR",mapobj.get("KR"));
                    tempobj.put("CV",mapobj.get("CV"));
                }else{
                    Map<String, Object>  mapobj=list.get(i+1);
                    tempobj.put("RZ",mapobj.get("RZ"));
                    tempobj.put("HW",mapobj.get("HW"));
                    tempobj.put("KR",mapobj.get("KR"));
                    tempobj.put("CV",mapobj.get("CV"));
                }
            }
        }
        List<Map<String,Object>> listout=wrpFieldinfoMapper.selectGSYllByTime(alarmparam);
        Map<String,Object> signobj=wrpFieldinfoMapper.selectDldSignByTime(alarmparam).get(0);
        Double[] outarray=new Double[list.size()];
        Double[] outq=new Double[list.size()];
        Integer sign=(Integer)signobj.get("SIGN");
        if(sign>=0){
            double toutq=((BigDecimal)signobj.get("Q")).doubleValue();
            for(int i=0;i<listout.size();i++){
                Map<String,Object> outobj=listout.get(i);
                double outoq=0;
                if(outobj.get("Q")==null){
                    outoq=toutq;
                }else{
                    outoq=((BigDecimal)outobj.get("Q")).doubleValue()+toutq;
                }
                outobj.put("Q",String.format("%.3f", outoq));
                outarray[i]=outoq*3600/10000;
                outq[i]=outoq;
            }
        }else{
            int digit=0;
            Map<String,Object> zeroobj=wrpFieldinfoMapper.selectZeroDldLlByTime(alarmparam).get(0);
            double zeroq=((BigDecimal)zeroobj.get("Q")).doubleValue();
            List<Map<String,Object>> listh=wrpFieldinfoMapper.selectDldQAndWByTime(alarmparam);
            if(listh==null || listh.size()==0){
                for(int n=0;n<outarray.length;n++){
                    outq[n]=zeroq;
                    outarray[n]=zeroq*3600/10000;
                }
            }else if(listh.size()==1 ){
                Map<String, Object> hisobj = listh.get(0);
                String curtime = (String) hisobj.get("TM") + ":00:00";
                double Sumq = ((BigDecimal) hisobj.get("SUMQ")).doubleValue();
                int Sumval = ((Integer) hisobj.get("SUMVAL")).intValue();
                double curq = ((BigDecimal) hisobj.get("LASTQ")).doubleValue();
                double afterlastq = ((BigDecimal) hisobj.get("LASTQ")).doubleValue();
                int differ = hourdiffer(alarmparam.getBegintime(), curtime);
                for (int m = 0; m < differ; m++) {
                    outarray[digit] = zeroq * 3600 / 10000;
                    outq[digit] = zeroq;
                    digit = digit + 1;
                }
                outarray[digit] = (Sumq + (3600 - Sumval) * zeroq) / 10000;
                outq[digit] = (Sumq + (3600 - Sumval) * zeroq) / 3600;
                int lastdiffer = hourdiffer(curtime, alarmparam.getEndtime());
                for (int m = 0; m < lastdiffer; m++) {
                    digit = digit + 1;
                    outq[digit] = afterlastq;
                    outarray[digit] = afterlastq * 3600 / 10000;
                }
                outq = reverse(outq);
                outarray = reverse(outarray);
            }else {
                for (int i = 0; i < listh.size() - 1; i++) {
                    Map<String, Object> hisobj = listh.get(i);
                    //int curhour=Integer.parseInt(((String)hisobj.get("TM")).split(" ")[1]);
                    String curtime = (String) hisobj.get("TM") + ":00:00";
                    double Sumq = ((BigDecimal) hisobj.get("SUMQ")).doubleValue();
                    int Sumval = ((Integer) hisobj.get("SUMVAL")).intValue();
                    double curq = ((BigDecimal) hisobj.get("LASTQ")).doubleValue();
                    if (i == 0) {
                        int differ = hourdiffer(alarmparam.getBegintime(), curtime);
                        for (int m = 0; m < differ; m++) {
                            outarray[digit] = zeroq * 3600 / 10000;
                            outq[digit] = zeroq;
                            digit = digit + 1;
                        }
                        outarray[digit] = (Sumq + (3600 - Sumval) * zeroq) / 10000;
                        outq[digit] = (Sumq + (3600 - Sumval) * zeroq) / 3600;
                    }
                    Map<String, Object> afterobj = listh.get(i + 1);
                    //int afterhour=Integer.parseInt(((String)afterobj.get("TM")).split(" ")[1]);
                    String aftertime = (String) afterobj.get("TM") + ":00:00";
                    double afterSumq = ((BigDecimal) afterobj.get("SUMQ")).doubleValue();
                    int afterSumval = ((Integer) afterobj.get("SUMVAL")).intValue();
                    double afterlastq = ((BigDecimal) afterobj.get("LASTQ")).doubleValue();
                    int afterdiffer = hourdiffer(curtime, aftertime);
                    for (int m = 1; m < afterdiffer; m++) {
                        digit = digit + 1;
                        outarray[digit] = curq * 3600 / 10000;
                        outq[digit] = curq;
                    }
                    digit = digit + 1;
                    outarray[digit] = (afterSumq + (3600 - afterSumval) * curq) / 10000;
                    outq[digit] = (afterSumq + (3600 - afterSumval) * curq) / 3600;
                    if (i == listh.size() - 2) {
                        int lastdiffer = hourdiffer(aftertime, alarmparam.getEndtime());
                        for (int m = 0; m < lastdiffer; m++) {
                            digit = digit + 1;
                            outq[digit] = afterlastq;
                            outarray[digit] = afterlastq * 3600 / 10000;
                        }
                    }
                }
                outq = reverse(outq);
                outarray = reverse(outarray);
            }
            for(int i=0;i<listout.size();i++){
                Map<String,Object> outobj=listout.get(i);
                double outoq=0;
                if(outobj.get("Q")==null){
                    outoq=outq[i];
                }else{
                    outoq=((BigDecimal)outobj.get("Q")).doubleValue()+outq[i];
                    outarray[i]+=((BigDecimal)outobj.get("WQ")).doubleValue()/10000;
                }
                outobj.put("Q",String.format("%.3f", outoq));
            }
        }
        //总入库流量  总出库流量
        double totalinw=0,totaloutw=0;
         //出库流量 入库流量整合进去
        List<Map<String,Object>> listin=wrpFieldinfoMapper.selectInWQByTime(alarmparam);
        for(int h=0;h<list.size();h++){
            //水库信息
            Map<String,Object> mapskobj=list.get(h);
            //出库流量
            Map<String,Object> outobj=listout.get(h);
            mapskobj.put("OTQ",outobj.get("Q"));
            //入库流量
            Map<String,Object> inobj=listin.get(h);
            if(inobj.get("RZ")==null){
                inobj.put("RZ",new BigDecimal(2710.9));
            }
            double insw=((BigDecimal)inobj.get("RZ")).doubleValue();
            totaloutw+=outarray[h];
     //       if(insw>=2710.95){
     //           Double curinq=Math.pow(insw/2710.7,1/0.00009);
     //           mapskobj.put("INQ",String.format("%.3f", curinq));
     //           totalinw+=curinq*3600/10000;
     //       }else{
                if(mapskobj.get("HW")==null){
                    mapskobj.put("INQ","");
                    totalinw+=0.0;
                }else{
                    Double curinw=((BigDecimal)mapskobj.get("HW")).doubleValue()+outarray[h];
                    Double curinq=curinw*10000/3600;
                    if(curinq<0.0){
                        curinq=0.0;
                        curinw=0.0;
                    }
                    totalinw+=curinw;
                    mapskobj.put("INQ",String.format("%.3f", curinq));
                }
     //       }
        }
        Map<String,Object> maptotal=new HashMap<>();
        maptotal.put("INW",String.format("%.3f", totalinw));
        maptotal.put("OUTW",String.format("%.3f", totaloutw));
        List<Map<String,Object>> stcdobj=wrpFieldinfoMapper.selectWaterSiteSwByStRsvr();
        Map<String,Object> map=new HashMap<>();
        map.put("total",maptotal);
        map.put("header",stcdobj);
        map.put("rows",list);
        return map;
    }
    //当前时间水库没有记录，取前一条记录
    public List<Map<String,Object>> selectStRsvrHByBefore(AlarmParam alarmparam){
        return wrpFieldinfoMapper.selectStRsvrHByBefore(alarmparam);
    }
    //两个日期相差小时
    public int hourdiffer(String begintime,String endtime){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin= null;
        Date end=null;
        try {
            begin = df.parse(begintime);
            end=df.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time=end.getTime()-begin.getTime();
        long hourdiffer=time/(1000*60*60);
        return (int)hourdiffer;
    }
    //数组反转
    public Double[] reverse(Double[] arr){
        Double[] arr1 = new Double[arr.length];
        for(int i = arr.length-1;i >= 0;i--){
            arr1[arr.length-i-1] = arr[i];
        }
        return arr1;
    }
}
