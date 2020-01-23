
def partyGroups = from("PartyNameView").where([partyTypeId: "PARTY_GROUP"]).queryList().take(5)
def partyPeople = from("PartyNameView").where([partyTypeId: "PERSON"]).queryList().take(5)

List parties = partyGroups + partyPeople
Collections.shuffle(parties)
context.parties = parties
