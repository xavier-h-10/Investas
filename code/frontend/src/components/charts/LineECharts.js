import React, {Component} from "react";
import {StyleSheet, Text, View} from "react-native";
import RNEChartsPro from "react-native-echarts-pro";
import {ScrollView} from "react-native";
import {Card} from "react-native-elements";
import {getFundNAV} from "../../service/FundService";

//用法 y轴刻度要单独算，根据近1月，近3月来决定范围
export default class LineECharts extends Component {
  constructor(props) {
    super(props);
    let timeRange = [2, 3, 4, 5, 7];
    this.state = {
      code: props.code,
      index:props.index,
      type: timeRange[props.index],
      lineOption:{},
    }
    // console.log("log=",this.state.code," ",this.state.type," ",props.index);
    getFundNAV(this.state.code, this.state.type, this.receiveData);
  }

  receiveData = (data) => {
    // console.log("line chart data received.");
    // console.log(data);
    let xAxisData = [];
    let yAxisData = [];
    let yAxisStockData = [];
    if(data.data==undefined || data.data.dailylist==undefined || data.data.dailylist.length==0 || data.data.stockdailylist.length==0) {
      return;
    }
    let len = data.data.dailylist.length;
    let base=data.data.dailylist[0].accumulateNAV;
    let mx=0.00;
    let mn=0.00;
    for (let i = 0; i < len; i++) {
      let tmp = data.data.dailylist[i];
      let tmp1=0.00;
      tmp1=((tmp.accumulateNAV-base)*100/base).toFixed(2);
      xAxisData.push(tmp.updateDate);
      yAxisData.push(tmp1);
      mx=Math.max(mx,tmp1);
      mn=Math.min(mn,tmp1);
    }

    //calculate stock n=daily info
    let stock_len = data.data.stockdailylist.length;
    let stock_base=data.data.stockdailylist[0].stockPrice;
    let stock_mx=0.00;
    let stock_mn=0.00;
    for (let i = 0; i < stock_len; i++) {
      let tmp = data.data.stockdailylist[i];
      let tmp1=0.00;
      tmp1=((tmp.stockPrice-stock_base)/stock_base*100).toFixed(2);
      // console.log(tmp1);
      yAxisStockData.push(tmp1);
      stock_mx=Math.max(stock_mx,tmp1);
      stock_mn=Math.min(stock_mn,tmp1);
    }

    //get the min and max of all
    let min_range=Math.min(mn,stock_mn);
    let max_range=Math.max(mx,stock_mx);

    let timeRange = [2, 3, 4, 5, 7];
    console.log("set state called type=",this.state.type);
    this.setState({
      code: this.state.code,
      index: this.state.index,
      type: this.state.type,
      lineOption: {
        label: {
          rich: {
            black: {
              color: '#070707',
            },
            green: {
              color: '#46a08b',
            },
            red: {
              color: '#e45e2e',
            }
          },
        },
        legend: {
          // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
          orient: 'horizontal',
          // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
          x: 'left',
          // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
          y: 'top',
          data: ['本基金', '同类均值', '沪深300'],
          left: 'center',
        },
        //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
        grid: {
          top: '16%',   // 等价于 y: '16%'
          left: '3%',
          bottom: '0%',
          containLabel: true
        },

        // 提示框
        tooltip: {
          show: true,
          trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
          axisPointer: {  //坐标轴指示器，坐标轴触发有效，
            // type: 'cross', //默认为line，line直线，cross十字准星，shadow阴影
            type:'line',
            snap:'true',   //设置自动吸附
            // axis:'y',
            crossStyle: {
              color: '#3364ff'
            }
          },
          // formatter:'{c}%',
          formatter:'{b}<br/>本基金：{c0}%<br/>沪深300：{c1}%',
        },
        xAxis: {
          type: 'category',
          splitNumber:2,
          axisLabel: {
            showMinLabel: true,
            showMaxLabel: true,
            color: '#d1d1d1',
            formatter: function(value,index) {
              if(value.length<10) return value;
              else return value.substring(5);
            }
          },
          // boundaryGap值为false的时候，折线第一个点在y轴上
          boundaryGap: false,
          data: xAxisData,
          axisLine: {
            onZero: false, //x轴不能在0上
            lineStyle: {
              color: '#f4f4f4',
              width: 1,
              //  shadowBlur:10,
            }
          },
        },

        yAxis: {
          type: 'value',
          //此处进行修改 保留小数效果不好
          min: Math.round((min_range-(max_range-min_range)*0.15)), // 设置y轴刻度的最小值
          max: Math.round((max_range+(max_range-min_range)*0.15)),  // 设置y轴刻度的最大值
          splitNumber: 5,  // 设置y轴刻度间隔个数
          axisLine: {
            lineStyle: {
              // 设置y轴颜色
              color: 'rgba(0,0,0,0.78)'
            }
          },
          axisLabel: {
            formatter: '{value}%',
            color: '#d3d3d3',
          },
          splitLine: {
            lineStyle: {
              type: 'dotted',
            }
          }
        },
        animationDuration: 500, //渲染时长调整
        series: [
          {
            name: '本基金',
            type: 'line',
            symbol: 'none',
            //    smooth:true,  //此处不加平滑，效果不太理想
            data: yAxisData,
            itemStyle: {
              normal: {
                lineStyle: {
                  width: 1,
                }
              }
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0, color: '#e7f0fd' // 0% 处的颜色
                }, {
                  offset: 1, color: '#fdfdfd' // 100% 处的颜色
                }],
                global: false // 缺省为 false
              },
              origin: 'start',
            },
            tooltip: {
                show:true,
                trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
                axisPointer: {  //坐标轴指示器，坐标轴触发有效，
                    type: 'cross', //默认为line，line直线，cross十字准星，shadow阴影
                    crossStyle: {
                        color: '#fff'
                    },
                },
              formatter:"{a}><br/>{b}:{c}({d}%)",
            },
          },

          {
            name: '沪深300',
            type: 'line',
            symbol:'none',
            //    smooth:true,
            data: yAxisStockData,
            itemStyle: {
              normal: {
                lineStyle: {
                  width: 1,
                }
              }
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0, color: '#e7f0fd' // 0% 处的颜色
                }, {
                  offset: 1, color: '#fdfdfd' // 100% 处的颜色
                }],
                global: false // 缺省为 false
              },
              origin: 'start',
            },
            tooltip: {
              show:true,
              trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
              axisPointer: {  //坐标轴指示器，坐标轴触发有效，
                type: 'cross', //默认为line，line直线，cross十字准星，shadow阴影
                crossStyle: {
                  color: '#fff'
                },
              },
            },
          },

        ],
          color: ['#3d76d1', '#2bb8d6', '#daae45'],
        // color: ['#3d76d1'],
      }
    });
  }

  componentWillReceiveProps(nextProps, nextState) {
    // console.log("nextProps=",nextProps);
    let timeRange=[2,3,4,5,7];
    if(nextProps.index==undefined || nextProps.index==this.state.index) {
      return;
    }
    this.setState({
      code: nextProps.code,
      index:nextProps.index,
      type: timeRange[nextProps.index],
      lineOption:this.state.lineOption,
    })
    // console.log("componentWillReceiveProps called ",nextProps.code,timeRange[nextProps.index]);
    getFundNAV(nextProps.code, timeRange[nextProps.index], this.receiveData);
  }

  render() {
    console.log("LineECharts rendered type=",this.state.type);

    return (

        <RNEChartsPro
            height={250}
            option={this.state.lineOption}
            onPress={res => alert(JSON.stringify(res))}
        />
        // <Card containerStyle={{height:300,paddingTop:25,borderRadius: 10}}>  //不能使用card,否则tooltip不能滑动
        // <View style={{alignItems:'center',width:'80%',minHeight:350}}>

    );



  }
}
