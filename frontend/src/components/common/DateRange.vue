<template>
<div style="display:flex">
	<Calendar :disabled="disabled" ref="begin" :value="begin_date" :end_date="end_date" @change="e => changeDate('begin', e)"/>
	<Calendar :disabled="disabled" ref="end" :value="end_date" :begin_date="begin_date" @change="e => changeDate('end', e)"/>
</div>
</template>

<script>
	import Calendar from "./Calendar.vue";
	export default {
		components: {Calendar},
		props: ['value', 'disabled'],
		watch: {
			value (newV, oldV) {
				this.begin_date = newV && this.$formatDate(newV[0]);
				this.end_date = newV && this.$formatDate(newV[1]);
				return newV;
			}
		},
		data() {
			return {
				begin_date: "",
				end_date: "",
			};
		},
		mounted () {
			this.begin_date = this.value && this.$formatDate(this.value[0]);
			this.end_date = this.value && this.$formatDate(this.value[1]);
			// this.$refs.end.clickMonth(1);
		},
		methods: {
      changeDate (type, e) {
				this[`${type}_date`] = e || "";
				if (this.begin_date && this.end_date) {
					this.$emit("change", [`${this.begin_date} 00:00:00`, `${this.end_date} 23:59:59`]);
				} else if (!this.begin_date && !this.end_date) {
					this.$emit("change", undefined);
				}
      },
		}
	}
</script>

<style lang="less">
</style>
