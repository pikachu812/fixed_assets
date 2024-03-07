<template>

    <div>
        <frontHeader v-permiss="15" />
        <v-header v-permiss="16" />
        <v-sidebar v-permiss="16" />

        <div class="content" v-permiss="15">
            <router-view v-slot="{ Component }">
                <component :is="Component"></component>
            </router-view>
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
import { useSidebarStore } from "../store/sidebar";
import { useTagsStore } from "../store/tags";
import vHeader from "../components/header.vue";
import vSidebar from "../components/sidebar.vue";
import vTags from "../components/tags.vue";
import FrontHeader from "../components/frontHeader.vue";

const sidebar = useSidebarStore();
const tags = useTagsStore();
</script>
