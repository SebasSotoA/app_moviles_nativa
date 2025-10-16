package com.app.episodic.common.data

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiDto: Entity): Domain
}

