export default function tenureCalculator(date) {
    const cur = new Date()
    const start = new Date(date)
    const tenure = Math.trunc((cur - start) / 1000 / 60 / 60 / 24)
    return (tenure / 365).toFixed(0) + '年' + (tenure % 365).toFixed(0) + '天'
}