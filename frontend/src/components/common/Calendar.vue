<template>
<div class="calendar">
	<div class="c-header">
    <span class="arrow arrow-left" @click="clickMonth(-1)"></span>
    <div>{{year}}-{{month}}</div>
    <span class="arrow arrow-right" @click="clickMonth(1)"></span>
	</div>
	<div class="week-view">
    <div v-for="(item, index) in list" :key="index">
			<div style="color: #515151">{{item}}</div>
		</div>
	</div>
  <div>
  	<div class="week-view" v-for="(weeks, index) in dateArray" :key="index">
			<div v-for="(item, index) in weeks" :key="index" @click="!disabled && clickDay(item)"
         class="day" :class="{nowMonth: month == item.month,
					 disabled: disabled,
           active: item.date == day || item.date == begin_date || item.date == end_date,
           selected: (month == item.month && day && begin_date && new Date(day) > new Date(item.date) && new Date(item.date) > new Date(begin_date))
            || (month == item.month && day && end_date && new Date(end_date) > new Date(item.date) && new Date(item.date) > new Date(day))
           }">
				<span>{{item && item.day}}</span>
			</div>
  	</div>
  </div>
</div>
</template>

<script>
const to10Num = (num) => {
  return num >= 10 ? `${num}` : `0${num}`;
}
	export default {
    // NOTE: onlyNowMonth true 只展示当月及其前后填补日期， fasle 包括当月整6个星期
		props: ["dataSource", "end_date", "begin_date", "onlyNowMonth", "value", "disabled"],
		watch: {
			dataSource (newV, oldV) {
				this.setDataSource(newV);
				return newV;
			},
			value (newV, oldV) {
        this.day = newV;
        if (newV) {
          this.year = Number(newV.split("-")[0]);
          this.month = Number(newV.split("-")[1]);
          this.init();
        }
				return newV;
			},
		},
		data() {
			let year = Number(this.$formatDate(new Date(), "YYYY"));
			let month = Number(this.$formatDate(new Date(), "MM"));
			// this.$emit("input", `${year}-${month}`);
			return {
        list: ["日", "一", "二", "三", "四", "五", "六"],
        dateArray: [],
				year,
				month,
        day: "",
			}
		},
		mounted () {
			this.init();
		},
		methods: {
      init () {
				let {year, month, dataSource} = this;
				dataSource = dataSource || [];
				let ym = new Date(`${year}-${month}`);
        let days = new Date(year, month, 0).getDate();
        let start = ym.getDay();
				// NOTE: 本月前面的日期
				let data = this.preMonth(start, dataSource);
				// NOTE: 插入本月日期
        for (let i = 0,obj,index; i < days; i++) {
					obj = {day: 1 + i < 10 ? `0${1+i}` : to10Num(1+i), week: (start + i) % 7, month, year, date: `${year}-${to10Num(month)}-${to10Num(1 + i)}`};
					index = dataSource.findIndex(e => this.$formatDate(e.date, "YYYY-MM-DD") === this.$formatDate(obj.date, "YYYY-MM-DD"));
					if (index !== -1) {
						obj.attend = dataSource[index].attend;
					}
          data.push(obj);
        }
        // NOTE: 填充，插入下个月日期
        if (this.onlyNowMonth) {
          data = data.concat(this.nextMonth(data[data.length - 1].week, dataSource, 7));
        } else {
          data = data.concat(this.nextMonth(data[data.length - 1].week, dataSource, data.length/7 > 5 ? 7 : 14));
        }

        this.setDataSource(data);
      },
			setDataSource (dataSource=[]) {
				let {dateArray } = this,index;

				// NOTE: 置空 分割 7天数组
				this.dateArray = [];
				for (let i = 0; i < dataSource.length; i+=7) {
					let arr = dataSource.slice(i, i + 7);
					// NOTE: 最后不足置空
					for (let j = 0; j < 7; j++) {
						arr[j] = arr[j] || "";
						if (arr[j].date) {
							arr[j].day = arr[j].date.split("-")[2];
						}
					}

					this.dateArray.push(arr);
				}
			},
			preMonth(start, dataSource) {
				let {year, month} = this;
				let format = this.$formatDate;
				if (month - 1 == 0) {
					year = year - 1;
					month = 12;
				} else {
					month = month - 1;
				}
        let days = new Date(year, month, 0).getDate();
				let arr = [];
				for (let i = 0,obj,index; i < start; i++) {
					obj = {day: to10Num(days + i - start + 1), week: i % 7, year, month, date: `${year}-${to10Num(month)}-${to10Num(days + i - start + 1)}`};
					index = dataSource.findIndex(e => format(e.date, "YYYY-MM-DD") === format(obj.date, "YYYY-MM-DD"));
					if (index !== -1) {
						obj.attend = dataSource[index].attend;
					}
					arr.push(obj);
				}
				return arr;
			},
      nextMonth (start, dataSource, showDays) {
				let {year, month} = this;
				let format = this.$formatDate;
				if (month + 1 == 13) {
					year = year + 1;
					month = 1;
				} else {
					month = month + 1;
				}
				let arr = [];
				for (let i = 0,obj,index; i < showDays - start - 1; i++) {
					obj = {day: to10Num(i+1), week: i + 1 % 7, year, month, date: `${year}-${to10Num(month)}-${to10Num(i+1)}`};
					index = dataSource.findIndex(e => format(e.date, "YYYY-MM-DD") === format(obj.date, "YYYY-MM-DD"));
					if (index !== -1) {
						obj.attend = dataSource[index].attend;
					}
					arr.push(obj);
				}
				return arr;
      },
      clickDay (day) {
        if (this.day == day.date) {
          this.day = "";
        } else {
          this.day = day.date;
        }
        if (day.month != this.month) {
          this.clickMonth(day.month - this.month);
        }
        this.$emit("change", this.day);
      },
			clickMonth (num) {
				if (this.month + num > 12) {
					this.year = this.year + 1;
					this.month = 1;
				} else if (this.month + num == 0) {
					this.year = this.year - 1;
					this.month = 12;
				} else {
					this.month = this.month + num;
				}
				this.init();
			}
		}
	}
</script>

<style lang="less">
@margin-item: 3px;
.calendar {
	padding: 0 10px;
}
.c-header {
	display: flex;
	align-items: center;
  justify-content: space-between;
	font-size: 16px;
	>div {
    flex: 1;
    text-align: center;
	}
}
.arrow {
  padding: 5px 10px;
  &::after {
    display: block;
    content: " ";
    width: 10px;
    height: 10px;
    border: 2px solid #333;
    border-left: transparent;
    border-bottom: transparent;
  }
}

&.arrow-left&::after {
  transform: rotate(-135deg);
}
&.arrow-right&::after {
  transform: rotate(45deg);
}
.week-view {
  display: flex;
  text-align: center;
	font-size: 16px;
  >div {
    flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
    color: rgba(0,0,0,.25);
    font-size: 14px;
    padding: 5px;
    margin: @margin-item;
		// >div {
		// 	display: inline-block;
		// 	width: 35px;
		// 	height: 35px;
		// 	display: flex;
		// 	flex-direction: column;
		// 	align-items: center;
		// 	justify-content: center;
		// 	border-radius: 8px;
		// 	margin-bottom: 10px;
		// }
  }
  .nowMonth {
    color: rgba(0,0,0,.65);
  }

  .day {
		user-select: none;
    position: relative;
    cursor: pointer;
    border-radius: 2px;
    >span {
      z-index: 1;
    }
    &:hover {
      background: #e6f7ff;
    }
    &.disabled {
			cursor: not-allowed;
	    &:hover {
	      background: none;
	    }
    }
    &.active {
      background: #1890ff!important;
      color: white;
    }
    &.selected {
      &::after {
        z-index: 0;
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        display: block;
        content: " ";
        height: 100%;
        width: 100%;
        width: calc(100% + @margin-item*4);
        margin-left: -@margin-item*2;
        background: #e6f7ff!important;
      }
    }
  }
}
</style>
