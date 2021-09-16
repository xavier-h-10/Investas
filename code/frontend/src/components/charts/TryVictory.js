import React from "react";
import {ScrollView, StyleSheet, View} from "react-native";
import { VictoryBar, VictoryChart, VictoryTheme } from "victory-native";

const data = [
  { quarter: 1, earnings: 13000 },
  { quarter: 2, earnings: 16500 },
  { quarter: 3, earnings: 14250 },
  { quarter: 4, earnings: 19000 }
];

export default class TryVictory extends React.Component {
  render() {
    return (
        <ScrollView
          horizontal={true}
        >
          <VictoryChart width={1000} theme={VictoryTheme.material}>
            <VictoryBar data={data} x="quarter" y="earnings" />
          </VictoryChart>
        </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5fcff"
  }
});
