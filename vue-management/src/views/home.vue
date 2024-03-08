<template>

  <div>
    <frontHeader v-permiss="15"/>
    <v-header v-permiss="16"/>
    <v-sidebar v-permiss="16"/>
    <div
        v-permiss="15"
        class="content-box1"
    >
      <div class="content1" v-permiss="15">
        <router-view v-slot="{ Component }">
          <component :is="Component"></component>
        </router-view>
      </div>
    </div>
    <div
        v-permiss="16"
        class="content-box"
        :class="{ 'content-collapse': sidebar.collapse }"
    >
      <v-tags></v-tags>
      <div class="content">
        <router-view v-slot="{ Component }">
          <transition name="move" mode="out-in">
            <keep-alive :include="tags.nameList">
              <component :is="Component"></component>
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>


</template>
<script setup lang="ts">
import {useSidebarStore} from "../store/sidebar";
import {useTagsStore} from "../store/tags";
import vHeader from "../components/header.vue";
import vSidebar from "../components/sidebar.vue";
import vTags from "../components/tags.vue";
import FrontHeader from "../components/frontHeader.vue";

const sidebar = useSidebarStore();
const tags = useTagsStore();
</script>

<style>

.content-box1 {
  position: absolute;
  left: 0;
  right: 0;
  top: 60px;
  bottom: 0;
  padding-bottom: 30px;
  -webkit-transition: left .3s ease-in-out;
  transition: left .3s ease-in-out;
  background: #ffffff;
}

.content1 {
  width: auto;
  height: 100%;
  padding: 10px;
  overflow-y: scroll;
  box-sizing: border-box;
}
</style>