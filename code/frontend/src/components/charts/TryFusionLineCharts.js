import React, { Component } from "react";
import {Platform, ScrollView, StyleSheet, Text, View} from "react-native";
import FusionCharts from "react-native-fusioncharts";

//矩形树图 重仓图表
//用法：传一个data进去，data:[{label:股票名称,value:占仓比例,svalue:涨跌幅}]
export default class TryFusionLineCharts extends Component {
  constructor(props) {
    super(props);
    //STEP 2 - Chart Data
    const chartData = [
      {
        fillcolor:"#e8e7e7",
        data:[
          {label:'贵州茅台',value:"50",svalue:"10"},    // 如果要采用相同颜色，可以使用fillcolor
          {label:'五粮液',value:"25",svalue:"-25"},
          {label:'泸州老窖',value:'20',svalue:"+5"},
          {label:'红星二锅头',value:'10',svalue:"-5"},
          {label:'酒鬼酒',value:'35',svalue:'-10'},
        ],
      },
    ];
    //STEP 3 - Chart Configurations
    const chartConfig = {
      type: "treemap",
      width: "400",
      height: "400",
      dataFormat: "json",
      dataSource: {
        chart: {
          theme: "fusion",
          algorithm:"squarified",
          plottooltext:"<b>$label</b><br>占比：$value%<br>涨跌幅：$svalue%",
          // chartLeftMargin:0,
          // chartTopMargin:0,
          // chartRightMargin:0,
          // chartBottomMargin:0,
          captionPadding:0,
          canvasTopPadding:0,

          // "labelFont": "Arial",
          labelFontSize: "12",
          "labelFontColor": "#ff0000",
          "labelFontBold": "1",
          "labelFontItalic": "1",
          //   showLegend:0,

          plotborderthickness:10,

          captionOnTop:1,
          captionAlignment:'left',
          alignCaptionWithCanvas: 1,
          captionHorizontalPadding: 2,
          caption: '重仓股票',
          subCaption: '2021-06-30（第二季度财报）',
        },
        data: chartData,
        colorrange: {
          mapbypercent: "1",
          gradient: "0.2",
          minvalue: "-25",
          startlabel:"跌幅",
          endlabel:"涨幅",
          code: "#62B58F",
          color: [
            {
              code: '#acffbc',
              maxvalue: "-0.1",
            },
            {
              code:'#ff9797',
              maxvalue:"0.1",
            },
            {
              code: '#f33430',
              maxvalue: "25",
            }
          ]
        },
      }
    };
    this.state = chartConfig;
    this.libraryPath = Platform.select({
      // Specify fusioncharts.html file location
      android: {
        uri: "file:///android_asset/fusioncharts.html"
      },
      // ios: require("../../ios/assets/fusioncharts-tpl.html")
    });
  }
  render() {
    return (

        <ScrollView
            horizontal={true}>
          <FusionCharts
              type={this.state.type}
              width={this.state.width}
              height={this.state.height}
              dataFormat={this.state.dataFormat}
              dataSource={this.state.dataSource}
              libraryPath={this.libraryPath} // set the libraryPath property
          />
        </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },

  header: {
    fontWeight: "bold",
    fontSize: 20,
    textAlign: "center",
  },

  chartContainer: {
    height: 400,
    borderColor: "#000",
  }
});
