import React, {Component} from "react";
import {Platform, ScrollView, StyleSheet, Text, View} from "react-native";
import FusionCharts from "react-native-fusioncharts";
import "../../../assets/fusioncharts-tpl.html";

//矩形树图 重仓图表
//用法：传一个data进去，data:[{label:股票名称,value:占仓比例,svalue:涨跌幅}]
//此处进行了接口改写 直接传数据 避免两次调用api  20210903
//TODO: ios适配 important
export default class FusionTreeMap extends Component {
  constructor(props) {
    super(props);
    if (props.data == undefined || props.data == null) {
      return;
    }
    let datas = props.data;
    let res = [];
    let len = datas.length;
    let mx = -100.00;
    let mn = 100.00;
    let total_value = 0.00;
    //占比: value  涨跌幅: value
    for (let i = 0; i < len; i++) {
      let value = parseFloat(datas[i].proportion).toFixed(2);
      let svalue = parseFloat(datas[i].value).toFixed(2);
      res.push({
        label: datas[i].name,
        value: value,
        svalue: svalue,
      });
      mx = Math.max(mx, datas[i].value);
      mn = Math.min(mn, datas[i].value);
      total_value += value;
    }
    // total_value=total_value.toFixed(2);
    let chartData = [
      {
        fillcolor: "#e8e7e7",
        // data: [{
        //   label: "总计",
        //   value: total_value,
        //   svalue: 0,
        //   data: res,
        // }],
        data: res,
      }
    ];
    let chartConfig = {
      type: "treemap",
      width: "400",
      height: "350",
      dataFormat: "json",
      dataSource: {
        chart: {
          theme: "fusion",
          algorithm: "squarified",
          plottooltext: "<b>$label</b><br>占比：$value%<br>涨跌幅：$svalue%",
          captionPadding: 0,
          canvasTopPadding: 0,

          // labelFontSize: "12",
          // "labelFontColor": "#ff0000",
          // "labelFontBold": "1",
          // "labelFontItalic": "1",
          showLegend: 0,
          showchildlabels: 1,
          labelFontSize: 12,
          labelFontColor: "#000000",

          plotborderthickness: 10,

          captionOnTop: 1,
          captionAlignment: 'left',
          alignCaptionWithCanvas: 1,
          captionHorizontalPadding: 2,
          caption: '重仓股票',
          subCaption: '2021-06-30（第二季度财报）',
        },
        data: chartData,
        colorrange: {
          mapbypercent: "1",
          gradient: "0.2",
          minvalue: mn,
          startlabel: "跌幅",
          endlabel: "涨幅",
          code: "#62B58F",
          color: [
            {
              code: '#acffbc',
              maxvalue: "-0.00001",
            },
            {
              code: '#ff9797',
              maxvalue: "0.00001",
            },
            {
              code: '#f33430',
              maxvalue: mx,
            }
          ]
        },
      }
    };
    this.state = (
        chartConfig
    );
    this.libraryPath = Platform.select({
      // Specify fusioncharts.html file location
      android: {
        uri: "file:///android_asset/fusioncharts.html"
      },
      ios: require("../../../assets/fusioncharts-tpl.html"),
    });
  }

  render() {
    if (this.state.dataSource == undefined || this.state.dataSource == null) {
      return null;
    }
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
