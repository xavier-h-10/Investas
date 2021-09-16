import React, {Component} from "react";
import {ButtonGroup, Card} from "react-native-elements";
import {Text, View, StyleSheet} from "react-native";
import LineECharts from "./LineECharts";
import MonetaryLineECharts from "./MonetaryLineECharts";

//props 传股票代码
export default class WrapLineECharts extends Component {
  constructor(props) {
    console.log("wrap line echarts",props);
    super(props);
    this.state = {
      selectedIndex: 3,
      code:props.code,
      type:props.type,
    }
    this.updateIndex = this.updateIndex.bind(this);
  }

  updateIndex(selectedIndex) {
    this.setState({
      selectedIndex: selectedIndex,
      code:this.state.code,
      type:this.state.type,
    })
  }

  renderEcharts = () => {
    if (this.props.type == undefined || this.props.type != 6) {
      return (
          <LineECharts code={this.props.code} index={this.state.selectedIndex}/>
      )
    } else {
        return (
            <MonetaryLineECharts code={this.props.code} index={this.state.selectedIndex} id={this.props.id}/>
        )
    }
  }

  render() {
    const buttons = [' 近1月 ', ' 近3月 ', ' 近6月 ', ' 近1年 ', ' 近3年 '];
    const {selectedIndex} = this.state;
    return (
        // <View style={{minHeight: 350}}>
        //   <Card containerStyle={{paddingTop: 25, borderRadius: 10}}>
            <View style={{flex: 1, alignItems: 'center'}}>
              {this.renderEcharts()}
              <ButtonGroup
                    onPress={this.updateIndex}
                    selectedIndex={selectedIndex}
                    buttons={buttons}
                    buttonContainerStyle={{borderWidth: 0, borderRadius: 20}}
                    buttonStyle={{borderWidth: 0, borderRadius: 20}}
                    innerBorderStyle={{width: 0}}
                    containerStyle={{
                      height: 40,
                      // borderRadius: 20,
                      borderWidth: 0,
                      paddingTop:15,
                      // backgroundColor:'black',
                    }}
                    selectedTextStyle={{color: '#6991c3'}}
                    textStyle={{color: '#a4a4a4'}}
                    selectedButtonStyle={{
                      backgroundColor: '#e9f3fe'
                    }}
                />
            </View>
        //   </Card>
        // </View>
    );
  }
}

const page = StyleSheet.create({
  text: {
    color: '#a4a4a4',
  }
});
