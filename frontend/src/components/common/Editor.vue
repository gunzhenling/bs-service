<template>
	<div style="text-align:left">
		<div ref="editor"></div>
		<Container v-if="view" :view="view"/>
		<!-- {{editImg}} -->
	</div>
</template>
<script>
	import Editor from "wangeditor";
	export default {
		props: ['value'],
		data() {
			return {
				is_update: false,
				qiniuHost: '',
				uploadToken: '',
				html: '',
				editImg: '',
				view: '',
			};
		},
	  beforeCreate: function () {
	    this.$options.components.Container = () => import('../Container.vue')
	  },
		mounted: async function () {
			// let token = await this._http.get("/api/v3/upload-token",{key: undefined});
      // if (window.location.protocol === 'https:') {
      //     this.qiniuHost = token.result.host[1];
      // } else {
      //     this.qiniuHost = token.result.host[0];
      // }
			// this.uploadToken = token.result.token;

			const editor = new Editor(this.$refs.editor);
			// 使用 onchange  标记更新
			editor.customConfig.onchange = html => {
				this.html = html;
				this.is_update = true;
				this.$emit("change", html);
			};
			editor.customConfig.zIndex = 1;
			editor.customConfig.showLinkImg = false;
			editor.customConfig.uploadFileName = 'file';

			editor.customConfig.uploadImgServer = this.qiniuHost;
			editor.customConfig.uploadImgParams = {
					// 如果版本 <=v3.1.0 ，属性值会自动进行 encode ，此处无需 encode
					// 如果版本 >=v3.1.1 ，属性值不会自动 encode ，如有需要自己手动 encode
					token: this.uploadToken
			};

			let menus = this.menus;
			// let {autoOpen = true,autoFocus = false,menus } = this.data;
			editor.customConfig.menus = Array.isArray(menus)
				? menus
				: [
						'head',  // 标题
						'bold',  // 粗体
						'fontSize',  // 字号
						'fontName',  // 字体
						'italic',  // 斜体
						'underline',  // 下划线
						'strikeThrough',  // 删除线
						'foreColor',  // 文字颜色
						'backColor',  // 背景颜色
						'link',  // 插入链接
						'justify',  // 对齐方式
						'emoticon',  // 表情
						'image',  // 插入图片
						'table',  // 表格
						'video'  // 插入视频
				];
			editor.customConfig.uploadImgHooks = {
				customInsert: function(insertImg, result, editor) {
					let url = result.url;
					insertImg(url);
				}
			};
			editor.create();
			try {// 给富文本添加功能
				window.editor = editor;
				editor.$textElem[0].addEventListener("dblclick", (e) => {
					let $img = editor._selectedImg;
					if ($img) {
						this.editImg = {
							src: $img[0].src, width: $img[0].style.width,
							href: editor.selection.getSelectionContainerElem().attr('href'),
							target: editor.selection.getSelectionContainerElem().attr('target') == '_blank',
						};
						this.initModal();
					}
					return e;
				});
			} catch (e) {
			}
			editor.txt.html(this.value);
			this.editor = editor;
		},
		methods: {
			initModal () {
				this.view = {
					component: "Modal",
					content: {
						visible: true,
						title: "编辑图片",
						click: (e) => {
							let {href, width, src, target} = e;
							let img = `<img style="${width ? `width:${width}` : `max-width:100%`}" src="${src}"/>`;
							if (href) {
								this.editor.cmd.do('insertHTML', `<a href="${href}" ${target ? `target="_blank"` : ``}> ${img}</a>`);
							} else {
								this.editor.cmd.do('insertHTML', img);
							}
							this.view = '';
						},
						content: {
							component: "Form",
							content: [
								{name: "跳转链接",key: "href", value: this.editImg.href || ''},
								{name: "图片宽度",key: "width", value: this.editImg.width || ''},
								{name: "图片地址",key: "src", value: this.editImg.src},
								{name: "新开页面",key: "target", type: 'switch', value: this.editImg.target},
							]
						}
					}
				}
			}
		}
	}
</script>

<style lang="less">
</style>
