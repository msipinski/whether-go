<template>
  <div>
    <h2>Aktywne miasta</h2>
    <b-btn @click="importForChosenCities()">Importuj aktywne</b-btn>
    <table style="margin-left: auto; margin-right: auto; border-spacing: 0 5px;">
        <tbody>
            <tr v-for="cityName in displayedActiveCities" :key="cityName">
                <td>
                    <span style="font-size: x-large;">{{ cityName }} </span>
                </td>
                <td>
                    <b-btn @click="importForCity(cityName)">Importuj</b-btn>
                </td>
                <td>
                    <b-btn @click="removeCity(cityName)">Usuń</b-btn>
                </td>
            </tr>
        </tbody>
    </table>
    <h2>Nieaktywne miasta</h2>
        <span>Strona {{ currentPage }} z {{ numberOfPages }}  </span>
        <input type="text" v-model="pageNrText" style="width: 100px;">
        <b-btn @click="changePage()">Wybierz</b-btn>
        <table style="margin-left: auto; margin-right: auto;">
            <tbody>
                <tr v-for="cityName in displayedInactiveCities" :key="cityName">
                    <td>
                        <span style="font-size: x-large;">{{ cityName }} </span>
                    </td>
                    <td>
                        <b-btn @click="addCity(cityName)">Dodaj</b-btn>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import {weatherService} from "@/services";

export default {
  name: "CityImporting",
  data() {
    return {
      activeCities: [],
      inactiveCities: [],
      displayedActiveCities: [],
      displayedInactiveCities: [],
      currentPage: 1,
      pageSize: 100,
      numberOfPages: 0,
      pageNrText: "",
    }
  },
  methods: {
    getActiveCities() {
        weatherService.getActiveCities().then(response => {
            this.activeCities.push(...response.data)
            this.updateDisplayedActiveCities()
        })
    },
    getInactiveCities() {
        weatherService.getInactiveCities().then(response => {
            this.inactiveCities.push(...response.data)
            this.updateDisplayedInactiveCities()
            this.numberOfPages = Math.ceil(this.inactiveCities.length / this.pageSize)
        })
    },
    addCity(cityName) {
        weatherService.addCity(cityName).then(response => {
            if(response.data) {
                let index = this.inactiveCities.indexOf(cityName)
                this.activeCities.push(String(cityName))
                this.activeCities.sort()
                this.inactiveCities.splice(index, 1)
            }
            this.updateDisplayedActiveCities()
            this.updateDisplayedInactiveCities()
        })
    },
    removeCity(cityName) {
        weatherService.removeCity(cityName).then(response => {
            if(response.data) {
                let index = 0
                for(let i = 0; i < this.inactiveCities.length; i++) {
                    let inactiveCity = this.inactiveCities[i]
                    if(inactiveCity > cityName) {
                        index = i
                        break
                    }
                    if(i == this.inactiveCities.length) {
                        index = i + 1
                    }
                }
                this.inactiveCities.splice(index, 0, cityName)
                index = this.activeCities.indexOf(cityName)
                this.activeCities.splice(index, 1)
            }
            this.updateDisplayedActiveCities()
            this.updateDisplayedInactiveCities()
        })
    },
    updateDisplayedActiveCities() {
        this.displayedActiveCities = this.activeCities.slice(0, 100)
    },
    updateDisplayedInactiveCities() {
        let startIndex = (this.currentPage - 1) * this.pageSize
        let endIndex = (this.pageSize * this.currentPage) - 1
        this.displayedInactiveCities = this.inactiveCities.slice(startIndex, endIndex)
    },
    changePage() {
        // Sprawdzam czy wprowadzone zostały tylko cyfry
        if(!(/^\d+$/.test(this.pageNrText))) {
            return
        }
        let desiredPageNr = parseInt(this.pageNrText)
        if(desiredPageNr > 0 && desiredPageNr <= this.numberOfPages) {
            this.currentPage = desiredPageNr
            this.updateDisplayedInactiveCities()
        }
    },
    importForChosenCities() {
        weatherService.importForChosenCities()
    },
      importForCity(cityName) {
        weatherService.importForCity(cityName)
    }
  },
  mounted() {
    this.getActiveCities(),
    this.getInactiveCities()
  }
}
</script>

<style scoped>

</style>